# Catch The Ball - DevSecOps

GitHub -> Jenkins -> Maven -> SonarQube -> Quality Gate -> Docker -> Trivy -> Amazon ECR

Local: `mvn clean package && java -jar target/catch-the-ball-1.0.0.jar`

Create ECR: `aws ecr create-repository --repository-name catch-the-ball --region ap-south-1`

Jenkins needs Pipeline, Git, Maven, Docker, SonarQube Scanner, AWS CLI and Trivy. Configure a SonarQube server named `SonarQube` and a Secret Text credential `aws-account-id`. Prefer an IAM role/instance profile for AWS credentials.

The application is Swing desktop software; Docker packages/builds it, but GUI execution inside a normal Linux container needs a display/X11 setup.
