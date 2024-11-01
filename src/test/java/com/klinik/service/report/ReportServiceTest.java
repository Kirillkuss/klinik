package com.klinik.service.report;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.LocalDateTime;
import java.util.List;
import org.hibernate.Session;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import com.klinik.entity.CardPatient;
import com.klinik.entity.RecordPatient;
import com.klinik.repositories.RecordPatientRepository;
import com.klinik.request.reports.ReportDrugTreatmentRequest;
import com.klinik.request.reports.ReportPatientRequest;
import com.klinik.response.ReportDrug;
import com.klinik.response.report.CardPatinetReport;
import com.klinik.response.report.ResponseReport;
import com.klinik.service.CardPatientService;
import io.qameta.allure.Allure;
import io.qameta.allure.Epic;
import io.qameta.allure.Owner;
import org.hibernate.jdbc.Work;

@Owner(value = "Barysevich K. A.")
@Epic(value = "Тестирование сервиса PatientService")
@DisplayName( value = "Тестирование сервиса PatientService")
public class ReportServiceTest {

    @Mock private EntityManager           entityManager;
    @Mock private CardPatientService      cardPatientService;
    @Mock private RecordPatientRepository recordPatientRepository;

    private ReportService reportService;

    private final String TYPE   = "application/json";
    private final String RESULT = "Результат: ";

    private final LocalDateTime dateFrom = LocalDateTime.now().minusDays(7);
    private final LocalDateTime dateTo = LocalDateTime.now();
    
    private Session           session;
    private Connection        connection;
    private CallableStatement callableStatement;
    private ResultSet         resultSet;

    private ReportDrugTreatmentRequest reportDrugTreatmentRequest;
    private ReportPatientRequest       reportPatientRequest;
    private RecordPatient              recordPatient;
    private CardPatient                cardPatient;
    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        session           = mock(Session.class);
        connection        = mock(Connection.class);
        callableStatement = mock(CallableStatement.class);
        resultSet         = mock(ResultSet.class);

        recordPatient  = new RecordPatient();
        cardPatient    = new CardPatient();
        reportService  = new ReportService(entityManager, cardPatientService, recordPatientRepository);
        reportDrugTreatmentRequest = new ReportDrugTreatmentRequest(dateFrom, dateTo);
        reportPatientRequest       = new ReportPatientRequest( 1L , dateFrom, dateTo );
        Mockito.when(entityManager.unwrap(Session.class)).thenReturn(session);
        Mockito.doAnswer( inv -> {
                (( Work ) inv.getArgument(0)).execute( connection );
                return null;
            }).when(session).doWork(any());
        Mockito.when( connection.prepareCall(any(String.class))).thenReturn( callableStatement );
    }

    @Test
    @DisplayName("Отчет по виду ребилитационного лечения за период времени")
    public void testGetStatReport() throws Exception {
        Mockito.when( callableStatement.getObject(3)).thenReturn( resultSet );
        Mockito.when( callableStatement.execute()).thenReturn(true );
        Mockito.when( resultSet.next()).thenReturn(true, false );
        Mockito.when( resultSet.getString("name_solution" )).thenReturn("Terapia 1");
        Mockito.when( resultSet.getLong("count_solution" )).thenReturn(10L);
        Mockito.when( resultSet.getLong("count_patient" )).thenReturn(5L);
        List<ResponseReport> reports = reportService.getStatReport(dateFrom, dateTo);
        Allure.addAttachment( RESULT, TYPE, reports.toString() );
        assertNotNull(reports);
        assertEquals(1, reports.size());
        Mockito.verify(entityManager).unwrap(Session.class);
        Mockito.verify(session).doWork(any());
        Mockito.verify(callableStatement).setTimestamp(1, Timestamp.valueOf(dateFrom));
        Mockito.verify(callableStatement).setTimestamp(2, Timestamp.valueOf(dateTo));
        Mockito.verify(callableStatement).registerOutParameter(3, Types.OTHER);
        Mockito.verify(callableStatement).execute();
        Mockito.verify(callableStatement).getObject(3);
        Mockito.verify(resultSet).getString("name_solution");
        Mockito.verify(resultSet).getLong("count_solution");
        Mockito.verify(resultSet).getLong("count_patient");
    }

    @Test
    @DisplayName("Отчет по медикаментозному лечению за промежуток времени")
    public void testReportStatDrug() throws Exception {
        Mockito.when( callableStatement.getObject(3)).thenReturn( resultSet );
        Mockito.when( callableStatement.execute()).thenReturn(true );
        Mockito.when( resultSet.next()).thenReturn(true, false );
        Mockito.when( resultSet.getString("name" )).thenReturn("Terapia 1");
        Mockito.when( resultSet.getLong("count_drug_treatment" )).thenReturn(10L);
        Mockito.when( resultSet.getLong("count_patient" )).thenReturn(5L);
        List<ReportDrug> reports = reportService.reportStatDrug( reportDrugTreatmentRequest );
        Allure.addAttachment( RESULT, TYPE, reports.toString() );
        assertNotNull( reports );
        assertEquals(1, reports.size());
        Mockito.verify( entityManager).unwrap(Session.class);
        Mockito.verify( session).doWork( any() );
        Mockito.verify( callableStatement ).setTimestamp(1, Timestamp.valueOf(dateFrom));
        Mockito.verify( callableStatement ).setTimestamp(2, Timestamp.valueOf(dateTo));
        Mockito.verify( callableStatement ).registerOutParameter(3, Types.OTHER);
        Mockito.verify( callableStatement ).execute();
        Mockito.verify( callableStatement ).getObject(3);
        Mockito.verify( resultSet ).getString("name");
        Mockito.verify( resultSet ).getLong("count_drug_treatment");
        Mockito.verify( resultSet ).getLong("count_patient");
    }

    @ParameterizedTest
    @CsvSource({"1"})
    @DisplayName("Отчет о полной информации по пациенту")
    public void testReportInformationAboutPatient( Long idCardPatient ) throws Exception {
        Allure.parameter( "idCardPatient", idCardPatient );
        Mockito.when( callableStatement.getObject(2)).thenReturn( resultSet );
        Mockito.when( callableStatement.execute()).thenReturn(true );
        Mockito.when( resultSet.next()).thenReturn(true, false );
        Mockito.when( resultSet.getString("name_solution" )).thenReturn("Terapia 1");
        Mockito.when( resultSet.getLong("count_solution" )).thenReturn(10L);
        CardPatinetReport reports = reportService.reportInformationAboutPatient( idCardPatient );
        Allure.addAttachment( RESULT, TYPE, reports.toString() );
        assertNotNull( reports );
        Mockito.verify( entityManager).unwrap(Session.class);
        Mockito.verify( session).doWork( any() );
        Mockito.verify( callableStatement ).setLong(1, idCardPatient);
        Mockito.verify( callableStatement ).registerOutParameter(2, Types.OTHER);
        Mockito.verify( callableStatement ).execute();
        Mockito.verify( callableStatement ).getObject(2);
        Mockito.verify( resultSet ).getString("name_solution");
        Mockito.verify( resultSet ).getLong("count_solution"); 
    }

    @Test
    @DisplayName("Отчет по записям пациента за период времени")
    public void testReportByPatietnWithRecordPatient() throws Exception {
        Allure.parameter( "reportPatientRequest", reportPatientRequest );
        Mockito.when( recordPatientRepository.findByParam( reportPatientRequest.getIdPatient(),
                                                            reportPatientRequest.getStart(),
                                                            reportPatientRequest.getEnd())).thenReturn( List.of(recordPatient));
        Mockito.when( cardPatientService.findByPatientId( reportPatientRequest.getIdPatient() )).thenReturn( cardPatient );
        assertNotNull( reportService.reportByPatietnWithRecordPatient( reportPatientRequest ));
        Allure.addAttachment( RESULT, TYPE, reportService.reportByPatietnWithRecordPatient( reportPatientRequest ).toString());
    }
    
}
