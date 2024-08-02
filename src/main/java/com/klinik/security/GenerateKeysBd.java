package com.klinik.security;

import java.util.Random;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.klinik.entity.KeyEntity;
import com.klinik.repositories.KeyEntityRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GenerateKeysBd {

    private final KeyEntityRepository keyEntityRepository;
    private final TextEncryptor textEncryptor;

   // Метод для сохранения ключей (шифрование)
   // @Transactional
    public KeyEntity saveKey(String keyAlias, String publicKey, String privateKey) {
             Random random = new Random();
             KeyEntity keyEntity = new KeyEntity();
             keyEntity.setId( random.nextLong());
             keyEntity.setAlice(keyAlias);
             keyEntity.setPublicKey(textEncryptor.encrypt( publicKey));
             keyEntity.setPrivateKey(textEncryptor.encrypt(privateKey));
             return keyEntityRepository.save(keyEntity);
    }

         // Метод для получения ключей (дешифровка)
         @Transactional
         public KeyEntity getKey(String keyAlias) {
             KeyEntity keyEntity = keyEntityRepository.findByKeyAlias(keyAlias).orElseThrow();
             if (keyEntity != null) {
                 keyEntity.setPublicKey(textEncryptor.decrypt(keyEntity.getPublicKey()));
                 keyEntity.setPrivateKey(textEncryptor.decrypt(keyEntity.getPrivateKey()));
             }
             return keyEntity;
         }


}
