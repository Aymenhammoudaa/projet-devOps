
pipeline{
    agent any
stages{
     stage('Récupération du code') {
            steps {
                git branch: 'karim',
                url: 'https://github.com/Aymenhammoudaa/projet-devOps.git'
            }
     }
      stage('Maven install') {
            steps {
                echo 'Nettoyage du Projet:'
                sh 'mvn install'
            }
        }
        stage('Maven Clean') {
            steps {
                echo 'Nettoyage du Projet:'
                sh 'mvn clean package'
            }
        }

       stage('Maven Test') {
            steps {
                echo 'Execution des Tests:'
                sh 'mvn test'
            }
        }

  stage('SonarQube') {
            steps {
                echo 'Analyse de la Qualité du Code : '
                sh 'mvn sonar:sonar -Dsonar.login=admin -Dsonar.password=Admin@dmin123'
            }
        }
stage('Nexus') {
            steps {
                echo 'build: '
                sh 'mvn clean deploy -DskipTests'
            }
        }

stage('Build Docker Image') {
            steps {
                script {
                    sh 'docker build -t karimby1/5erpbi7-g7-tpfoyer .'
                }
            }
        }
stage('push docker image to docker hub') {
    steps {
        withCredentials([usernamePassword(credentialsId: 'dockerhub-cred', usernameVariable: 'docker_username', passwordVariable: 'docker_password')]) {
            sh 'echo "$docker_password" | docker login -u "$docker_username" --password-stdin'
            sh 'docker push karimby1/5erpbi7-g7-tpfoyer:latest'
        }
    }
}

    stage('Docker Compose Down') {
            steps {

                    sh 'docker compose down'
                }

        }

        stage('Deploy with Docker Compose') {
            steps {

                    sh 'docker compose up -d'

            }
        }

        stage('Promethus & Grafana') {
            steps {
                    script {
                      sh 'docker start prometheus'
                      sh 'docker start grafana'
                    }
                }
            }

}
}