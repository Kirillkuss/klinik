package com.klinik.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.tngtech.archunit.base.DescribedPredicate;
import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
//import com.tngtech.archunit.junit.AnalyzeClasses;
//import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import io.qameta.allure.Allure;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;


//@AnalyzeClasses(packages = "com.klinik")
public class TreatmentServiceTest {

    private JavaClasses importedClasses;

    /**@BeforeEach
    public void setup() {
        importedClasses = new ClassFileImporter()
                .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
                .importPackages("com.klinik");
    }*/

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {
        JavaClasses noClasses = new ClassFileImporter().importPackages("com.klinik");
        //JavaClasses noClasses = new ClassFileImporter().importPaths( "/com/klinik");
     
     ArchRule r1 =  classes().that().resideInAPackage("com.klinik.service").should().onlyBeAccessed().byAnyPackage("com.klinik.controller", "com.klinik.service");
        r1.check( noClasses );


       System.out.println( " ArchRule >>> " + r1);
        Allure.addAttachment("Результат:", "text/plain",  r1.toString() );
    }


   // @ArchTest
  //  public static final ArchRule myRule = classes()
  //      .that().resideInAPackage("..service..")
      //  .should().onlyBeAccessed().byAnyPackage("..controller..", "..service..");


    //@ArchTest
    //public static final ArchRule myRule = classes()
    //    .that().resideInAPackage("..service..")
    //    .should().onlyBeAccessed().byAnyPackage("..controller..", "..service..");

    
}
