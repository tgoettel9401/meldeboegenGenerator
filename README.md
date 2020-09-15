# meldeboegenGenerator

This repository is for generating Meldeboegen used during DLM (Deutsche Ländermeisterschaft). 

For using the repository, please clone it first. <br>
`git clone https://github.com/tgoettel9401/meldeboegenGenerator.git`

Then create the image using Docker-CLI (you need to have Docker installed and running): <br>
`docker build -t dsj/meldeboegen-generator .`

Finally run the container: <br>
`docker run -p 8080:8080 dsj/meldeboegen-generator`

Importing player- and team-data is possible 
- Http-Method: POST
- URI: localhost:8080/importPlayersAndTeams
- Body: form-data with key file and standard LST-export (either of DSJ-Website for ANREISE or SwissChess for ROUND)

Generating the PDFs: 
- Open in Browser: localhost:8080/generateAnreiseBogen and localhost:8080/generateRundeBogen (depending on the Meldebogen you want to generate)
