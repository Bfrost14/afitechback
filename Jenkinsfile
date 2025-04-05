pipeline {
    agent any

    environment {
        NAME = 'myafiback'
        NAMEF = 'myafifront'
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

        stage('INSTALL NPM') {
                    steps {
                    dir('myafifront') {
                        sh  "npm install --legacy-peer-deps"
                    }
                    }
                }

                stage('BUILD NPM') {
                    when { branch 'master' }
                    steps {
                    dir('myafifront') {
                        sh  "npm run build-prod"
                    }
                    }
                }


        stage('[DEV] BUILD DOCKER') {
            when { branch 'master' }
            steps {
                sh "docker build -t ${NAME}-dev:${VERSION} -f myafiback/Dockerfile myafiback"
                sh "docker build -t ${NAMEF}:latest -f myafifront/Dockerfile myafifront"
            }

        }

        stage('[DEV] RUN DOCKER') {
             when { branch 'master' }
            steps {
                sh  "docker rm -f ${NAME}-dev"
                sh  "docker run --network ${NAME_NETWORK} --name ${NAME}-dev -v ${VOLUME}:/data --restart=always -e JAVA_OPTS='-Dspring.profiles.active=prod -Dspring.cloud.config.label=develop -Dserver.port=8095'  -p ${PORT}:8095 -d ${NAME}-dev:${VERSION}"
            }
        }

                stage('RUN DOCKER FRONT') {
                     when { branch 'master' }
                    steps {
                        sh  "docker rm -f ${NAMEF}"
                        sh  "docker run  --name ${NAMEF} -p 4240:80 -d ${NAMEF}:latest"

                    }
                }

    }


}
