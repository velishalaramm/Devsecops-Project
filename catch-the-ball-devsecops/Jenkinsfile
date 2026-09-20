pipeline {
  agent any
  environment {
    AWS_REGION='ap-south-1'
    AWS_ACCOUNT_ID=credentials('aws-account-id')
    ECR_REPOSITORY='catch-the-ball'
    IMAGE_TAG="${BUILD_NUMBER}"
  }
  stages {
    stage('Checkout'){steps{checkout scm}}
    stage('Build'){steps{sh 'mvn -B clean package'}}
    stage('SonarQube'){steps{withSonarQubeEnv('SonarQube'){sh 'mvn -B sonar:sonar -Dsonar.projectKey=catch-the-ball -Dsonar.projectName="Catch The Ball"'}}}
    stage('Quality Gate'){steps{timeout(time:5,unit:'MINUTES'){waitForQualityGate abortPipeline:true}}}
    stage('Docker Build'){steps{sh 'docker build -t ${ECR_REPOSITORY}:${IMAGE_TAG} -t ${ECR_REPOSITORY}:latest .'}}
    stage('Trivy FS Scan'){steps{sh 'trivy fs --exit-code 1 --severity HIGH,CRITICAL --ignore-unfixed .'}}
    stage('Trivy Image Scan'){steps{sh 'trivy image --exit-code 1 --severity HIGH,CRITICAL --ignore-unfixed ${ECR_REPOSITORY}:${IMAGE_TAG}'}}
    stage('Push ECR'){steps{sh '''aws ecr get-login-password --region ${AWS_REGION} | docker login --username AWS --password-stdin ${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_REGION}.amazonaws.com
docker tag ${ECR_REPOSITORY}:${IMAGE_TAG} ${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_REGION}.amazonaws.com/${ECR_REPOSITORY}:${IMAGE_TAG}
docker tag ${ECR_REPOSITORY}:latest ${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_REGION}.amazonaws.com/${ECR_REPOSITORY}:latest
docker push ${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_REGION}.amazonaws.com/${ECR_REPOSITORY}:${IMAGE_TAG}
docker push ${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_REGION}.amazonaws.com/${ECR_REPOSITORY}:latest'''}}
  }
  post {always {archiveArtifacts artifacts:'target/*.jar',fingerprint:true,allowEmptyArchive:true; sh 'docker image prune -f || true'}}
}
