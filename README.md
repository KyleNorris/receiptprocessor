# Instructions to run receipt processor 
## Run the following command to build docker image 
docker build -t receipt-processor .

## Run the following command to run the application in a docker container
docker run -p 8080:8080 receipt-processor  
