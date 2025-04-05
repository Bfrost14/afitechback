pipeline {
    agent any

    environment {
        NAME = 'myafiback'
        VERSION = '3.3.2'
        NAME_NETWORK = 'bfrost_ibr'
        PORT = 8095
        VOLUME = '/data/myafiback'
        CONTEXT_PATH = '/myafiback/'
        EMAIL = 'noreply@bfrost.net'
    }

    options {
      timeout(time: 120, unit: 'MINUTES')
    }

    stages {

        stage('Clean Install') {
            steps {
                dir('myafiback') {
                    sh  "/usr/share/maven/bin/mvn clean install -DskipTests"
                    stash includes: '**/target/*', name: 'target'
                }
            }
        }



        stage('[DEV] BUILD DOCKER') {
            when { branch 'master' }
            steps {
                sh  "docker build -t ${NAME}-dev:${VERSION} ."
            }

        }

        stage('[DEV] RUN DOCKER') {
             when { branch 'master' }
            steps {
                sh  "docker rm -f ${NAME}-dev"
                sh  "docker run --network ${NAME_NETWORK} --name ${NAME}-dev -v ${VOLUME}:/data --restart=always -e JAVA_OPTS='-Dspring.profiles.active=prod -Dspring.cloud.config.label=develop -Dserver.port=8095'  -p ${PORT}:8095 -d ${NAME}-dev:${VERSION}"
            }
        }

    }


}
