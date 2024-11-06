pipeline {
  agent 'any'
   tools{
     jdk 'jdk 17'
 }
  stages {
    stage('Checkout') {
      steps {
        script {
            checkout([$class: 'GitSCM', branches: [[name: '*/gradletest']], userRemoteConfigs: [[url: 'https://github.com/Kirillkuss/klinik']]])
        }
      }
    }
    
    stage('Clean') {
      steps {
        bat(script: 'gradlew clean')
      }
    }
    
    stage('Build') {
      steps {
        bat(script: 'gradlew build')
      }
    }
    
    stage('Test and Allure Report'){
        steps{
            bat(script: 'gradlew test')
            allure includeProperties: false, jdk: '', properties: [[key: 'allure.results.directory', value: 'allure-results']], report: 'allure-report', results: [[path: 'allure-results']]
        }
    }
        
    stage('JaCoCo Report') {
        steps {
           archiveArtifacts artifacts: 'build/reports/jacoco/test/html/**', fingerprint: true
        }
    }    

    }
    

    post {
        always {
            junit allowEmptyResults: true, skipPublishingChecks: true, testResults: 'build/test-results/test/TEST-*.xml' 
        }
    }
}
