pipeline {
  agent none

  stages {
    stage('Test') {
        agent {
            ecs {
                inheritFrom 'ecs'
            }
        }
        steps {
            sh 'sbt checksum/assembly'
            sh 'sbt virus/assembly'
            sh 'sbt fileformat/assembly'
            withCredentials([[$class: 'AmazonWebServicesCredentialsBinding', credentialsId: 'aws',
                        accessKeyVariable: 'AWS_ACCESS_KEY_ID', secretKeyVariable: 'AWS_SECRET_ACCESS_KEY']]) {
                sh 'aws lambda update-function-code --function-name arn:aws:lambda:eu-west-2:247222723249:function:tdr-checksum-dev --zip-file fileb://checksum/target/scala-2.12/checksum-assembly-0.1.0-SNAPSHOT.jar'
                sh 'aws lambda update-function-code --function-name arn:aws:lambda:eu-west-2:247222723249:function:tdr-fileformat-dev --zip-file fileb://fileformat/target/scala-2.12/fileformat-assembly-0.1.0-SNAPSHOT.jar'
                sh 'aws lambda update-function-code --function-name arn:aws:lambda:eu-west-2:247222723249:function:tdr-virus-dev --zip-file fileb://virus/target/scala-2.12/virus-assembly-0.1.0-SNAPSHOT.jar'
            }
            
        }
    }
  }
}
