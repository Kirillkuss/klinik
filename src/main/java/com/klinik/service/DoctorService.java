package com.klinik.service;

import com.klinik.aspect.GlobalOperation;
import com.klinik.entity.Doctor;
import com.klinik.excep.MyException;
import com.klinik.mapper.DoctorImpl;
import com.klinik.mapper.DoctorMapper;
import com.klinik.redis.repository.DoctorRepositoryRedis;
import com.klinik.repositories.DoctorRerository;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Service
@CacheConfig(cacheNames={"doctors"})
public class DoctorService {

    @Autowired private DoctorRerository      doctorRerository;
    @Autowired private EntityManager         entityManager;
    @Autowired private DoctorRepositoryRedis doctorRepositoryRedis;
    @Autowired private DoctorImpl            doctorImpl;

    @Cacheable(keyGenerator = "customKeyGenerator") 
    @GlobalOperation(operation = "allDoctor")
    public List<Doctor> allDoctor(int page, int size ){
        // 3. Method execution time - 60 - 120 ms                                     
        List<Doctor> response = doctorRerository.findAll( PageRequest.of( page - 1, size )).getContent();
        return response;                                       
    }

    
    /**
     * Ленивая загрузка 
     * @param page - страница
     * @param size - размер 
     * @return List<Doctor>
     * CacheEvict - allEntries - очищаем все записи в кеше
     */
    @CacheEvict(allEntries=true)
    @GlobalOperation(operation = "getLazyDoctors")
    @SuppressWarnings("unchecked")
    public List<Doctor> getLazyDoctor( int page, int size ){
        return entityManager.createNativeQuery( "select * from Doctor", Doctor.class)
                            .setFirstResult((page - 1) * size)
                            .setMaxResults(size)
                            .getResultList();
    }

    @Cacheable(keyGenerator = "customKeyGenerator")
    @GlobalOperation(operation = "getCountDoctors")
    public Long getCountDoctors(){
        // Method execution time ~ 60 ms 
        long response =  doctorRerository.count();
        // Method execution time ~ 2400 ms 
        //long response =  doctorRerository.findAll().stream().count();
        // Method execution time ~ 60 ms 
        //long response = (long)entityManager.createNativeQuery( "select COUNT(*) from Doctor", Long.class).getResultList().stream().findFirst().orElse( null );
        return response;
    }
    /**
     * Поиск по ФИО
     * 
     * @param word - слово
     * @param page - страница
     * @param size - размер
     * @return List<Doctor>
     * @throws Exception
     * 
     * Разница между @Cacheable и @CachePut заключается в том, что @Cacheable пропустит запуск метода ,
     * тогда как @CachePut фактически запустит метод , а затем поместит его результаты в кеш.
    */
    @CachePut(keyGenerator = "customKeyGenerator")
    @GlobalOperation(operation = "findByFIO")
    public List<Doctor> findByFIO( String word, int page, int size ) throws Exception{
        List<Doctor> response = doctorRerository.findDoctorByFIO( word )
                                                .stream()
                                                .skip(( page - 1 ) * size )
                                                .limit( size )
                                                .toList();
        if( response.isEmpty() ) throw new MyException( 404, "По данному запросу ничего не найдено");
        return response;
    }
    /**
     * Помещаем кэш в хранилище Redis
     * @param idDoctor
     * @return
     */
    public Doctor findByIdDoctor( Long idDoctor ){
        Doctor doctor = new Doctor();
         if ( doctorRepositoryRedis.findById( idDoctor.toString() ).isEmpty() ){
            doctor = doctorRerository.findById( idDoctor ).orElseThrow( () -> new NoSuchElementException( "Doctor not found"));
            doctorRepositoryRedis.save( doctorImpl.doctorToRedis( doctor ));
            log.info( "Save redisDoctor ");
        }else{
            com.klinik.redis.model.Doctor redisDoctor = doctorRepositoryRedis.findById( idDoctor.toString() ).orElse( null );
            doctor = redisDoctor.getDoctor();
        }
        return doctor;
    }

    @CachePut
    @GlobalOperation(operation = "saveDoctor")
    public Doctor saveDoctor( Doctor doctor ) throws Exception{
        if ( doctorRerository.findById( doctor.getIdDoctor() ).isPresent()) throw new MyException( 409, "Пользователь с таким ИД уще существует");
        Doctor response = doctorRerository.save( doctor );
        //doctorRepositoryRedis.save( new com.klinik.redis.model.Doctor( response.getIdDoctor().toString(), response, LocalDateTime.now()));
        //System.out.println( doctorRepositoryRedis.findAll() );
        return response;
    }

}
