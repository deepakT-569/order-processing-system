pipeline {
    agent any
    stages {
        stage('Build') {
            steps { sh 'mvn clean package -DskipTests' }
        }
        stage('Docker Build & Push') {
            steps {
                sh 'docker build -t yourdockerhub/order-service:latest order-service/.'
                sh 'docker push yourdockerhub/order-service:latest'
            }
        }
        stage('Deploy to Kubernetes') {
            steps { sh 'kubectl apply -f k8s/' }
        }
    }
}
