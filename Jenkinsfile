pipeline {
  agent 'any'
   tools{
     jdk 'jdk 17'
 }
  stages {
    stage('Checkout') {
      steps {
        script {
            checkout([$class: 'GitSCM', branches: [[name: '*/Jenkins']], userRemoteConfigs: [[url: 'https://github.com/Kirillkuss/klinik']]])
        }
      }
    }
    
    stage('Clean') {
      steps {
        bat(script: 'mvn clean')
      }
    }
    
    stage('Package') {
      steps {
        bat(script: 'mvn package')
      }
    }

    
  }
    post {
        always {
            allure includeProperties: false, jdk: '', properties: [[key: 'allure.results.directory', value: 'target/allure-results']], report: 'target/allure-report', results: [[path: 'target/allure-results']]
            junit(testResults: '**/target/surefire-reports/*.xml', allowEmptyResults : true, skipPublishingChecks: true)
            }
        }
}