# operacion_fuego_estrella_neut

Para ejecutar el programa:

1. Clonar el repositorio
2. Instalar Gradle
3. Generar ejecutable con el comando gradle build
4. Dentro de la carpeta build/libs ejecutar el comando java -jar OperacionFuegoEstrellas-0.0.1-SNAPSHOT.jar

Nivel 2
Para probar el nivel dos, después de ejecutar el programa, se llama al servicio para obtener la ubicación y mensaje de la nave entre los tres satélites a través de la dirección: 

http://localhost:8080/topsecret

El servicio es de tipo POST y debe tener un body JSON con la siguiente estructura: 

{
"satellites": [
{
"name": "kenobi",
"distance": 100.0,
"message": ["este", "", "", "mensaje", ""]
},
{
"name": "skywalker",
"distance": 115.5,
"message": ["", "es", "", "", "secreto"]
},
{
"name": "solo",
"distance": 142.7,
"message": ["este", "", "un", "", ""]
}
]
}


 




Nivel 3
El nivel 3 se divide en dos partes, la primera es un servicio de tipo POST que nos permitirá llenar la información de cada uno de los satélites por separado y que se usa a través de la siguiente dirección: 
http://localhost:8080/topsecret_split/{nombre_satelite}

Se debe tener un body de tipo JSON con la siguiente estructura: 
{
"distance": 267.0,
"message": ["este", "", "", "mensaje", ""]
}
 

La segunda parte del nivel, consiste en un servicio de tipo GET para obtener la ubicación de la nave entre los 3 satélites solo si se ha llenado la información de cada uno de los tres satélites desde el anterior servicio. Se usa a través de la siguiente dirección: 
http://localhost:8080/topsecret_split
 






