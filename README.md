
# Weather Report

This project fetches data from https://openweathermap.org/ and processes it with the help of an internal database. The user can get a weather forecast for the city or for the entire region. It is also possible to update cities, such as changing the name or moving the city to another region.


## Demo

![Demo](https://martinpersonalweb.vercel.app/_next/image?url=%2Fimages%2FWeatherReportGIF.gif&w=3840&q=75)


## Internal API Reference

#### Add new City

```http
  POST /addCity
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `cityName` | `string` | send it in body |

#### Save weather to database

```http
  POST /weather
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `city,countryCode`      | `JSON` | **Calls external API** all parameters are required |

```http
  POST /update
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `currentName, newName, region, population` | `JSON` | **Required** currentName |

```http
  POST /get/region/{region}
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `region` | `string` | weather forecast for region |


```http
  POST /get/city/{city}
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `city` | `city` | weather forecast for city |


#### add(num1, num2)


Takes two numbers and returns the sum.

/get/region/trnava
## Environment Variables

To run this project, you will need to add the following environment variables to your .env file

`API_KEY`

`MYSQLDB_NAME`

`MYSQLDB_ROOT_USER`

`MYSQLDB_ROOT_PASSWORD`


## Run Locally

Clone the project

```bash
  git clone https://github.com/MartinBlazek6/WeatherReport.git
```

Install dependencies

```bash
  npm i
```

Start the back end server

```bash
  mvn spring-boot:run
```

Start the front end server

```bash
  npm run dev
```

