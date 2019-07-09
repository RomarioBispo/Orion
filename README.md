# Framework to FIWARE platform
This framework was developed as a undergraduate thesis at Federal University of Sergipe.

This project is a framework to FIWARE platform. Your objective is to give a way to develop easier FIWARE applications using the Programming Language java. 
## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

Before put hands on the code and use the framework on local machine, you must to install the following:

- Docker;
- Docker Compose;
- Docker Images (Fiware, MongoDB, IoT-A) from Docker Hub;
- Java (JDK and SDK);
- An Java IDE;

Notes: To use this framework, you need to know java programming language and know about fiware and your components. All the things are made on linux ubuntu.

### Installing

Here, i will give you a hand and show how to install docker and other dependencies:

The first thing you to install Docker using the repository is to update the apt package index:
```
$ sudo apt-get update
```
Second, install the latest version of Docker CE and containerd
```
$ sudo apt-get install docker-ce docker-ce-cli containerd.io
```
Third, verify that Docker CE is installed correctly by running the hello-world image:
```
$ sudo docker run hello-world
```

To install Docker compose:

First, run this command to download the current stable release of Docker Compose:


```
$ sudo curl -L "https://github.com/docker/compose/releases/download/1.24.0/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose

```

Second, apply executable permissions to the binary:
```
sudo chmod +x /usr/local/bin/docker-compose
```

Test the installation:
```
$ docker-compose --version
```

To install The images from Docker-compose, run the following command:

```
$ docker-compose --version
git clone git@github.com:FIWARE/tutorials.Getting-Started.git
```

```
cd tutorials.Getting-Started
```

```
docker-compose -p fiware up -d
```


To more information about install Docker, see:

https://docs.docker.com/install/

To  more information about install compose, see:

 https://docs.docker.com/compose/install/

To more information about install FIWARE images, you can see this great tutorial from FIWARE availabe on:

https://fiware-tutorials.readthedocs.io/en/latest/getting-started/index.html
## Running the tests

Let's recapitulate, you've installed docker, docker compose and the images from FIWARE. Now, you can clone the repository.

### How to configure the listener:

We need to configure the listener, but if you don't know what is a listener, you must read the documentations available at: SITE FROM JAVADOCS.
```
Give an example
```

### And coding style tests

Explain what these tests test and why

```
Give an example
```

## Deployment

Add additional notes about how to deploy this on a live system

## Built With

* [Intellij](https://www.jetbrains.com/idea/) - The java IDE used.
* [Maven](https://maven.apache.org/) - Dependency Management.

## Contributing
If you wanto to contribute to this project, please contact me. Anyone is welcome to help.
## Versioning

We use [Github](https://github.com/) for versioning. For the versions available, see the [tags on this repository](https://github.com/your/project/tags). 

## Authors

* **Romário Bispo** - [Romário Bispo](https://github.com/RomarioBispo)

COLOCAR LISTA DE CONTRIBUIDORES DO PROJETO

See also the list of [contributors](https://github.com/your/project/contributors) who participated in this project.

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details

## Acknowledgments

* I used some open source codes by Felipe Matheus and Mariana Martins.

