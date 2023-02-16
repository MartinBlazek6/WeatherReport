<script>/**
 * @type {string}
 */


let parameter = '';
let citiesData = [];
let errorMessage = '';
let city = "";
let data = null;

const handleSubmit = async () => {
    try {
        const response = await fetch(`http://localhost:8081/get/region/${parameter}`);

        // if (!response.ok) {
        //     throw new Error('Network response was not ok');
        // }

        citiesData = await response.json();
        console.log('Successfully fetched data:', citiesData);
    } catch (error) {
        console.error('An error occurred:', error);
        errorMessage = error.message;
    }
};

async function fetchData() {
    const url = `http://localhost:8081/get/city/${city}`;
    const response = await fetch(url);
    const jsonData = await response.json();
    data = jsonData;
}

</script>

<h2>Search Weather report by region</h2>
<input type="text" bind:value={parameter} on:input={handleSubmit} placeholder="Enter region name"/>

<h2>Search Weather report by City</h2>
<input type="text" bind:value={city} on:input={fetchData} placeholder="Enter city name"/>

{#if data}
    <h1>Weather report for city {data.name}</h1>
    <br>
    <div>
        <div class="weather-card-lonely">
            <div class="coordinates">
                <p>Longitude: <span >{data.weather.longitude}</span></p>
                <p>Latitude: <span >{data.weather.latitude}</span></p>
            </div>
            <div class="temperature">
                <h1 >{data.weather.temp}°C</h1>
                <div class="min-max-temperature">
                    <p>Min: <span >{data.weather.minTemp}°C</span></p>
                    <p>Max: <span >{data.weather.maxTemp}°C</span></p>
                </div>
            </div>
        </div>
    </div>
{/if}

{#if citiesData.length}
    <ul>
        <h1>Weather report for selected region</h1>
        <div class="card-container">
        {#each citiesData as city}


<!--                    <div class="weather-card">-->

                        <div class="weather-card">
                            <div class="location">
                                <h2 id="city-name">{city.name}</h2>
                            </div>
                            <div class="temperature">
                                <h1 id="current-temperature">{city.weather.temp}°C</h1>
                                <div class="min-max-temperature">
                                    <p>Min: <span id="min-temperature">{city.weather.minTemp}°C</span></p>
                                    <p>Max: <span id="max-temperature">{city.weather.maxTemp}°C</span></p>
                                </div>
                            </div>
                        </div>

        {/each}
        </div>
    </ul>
{:else if errorMessage}
    <!--    <p class="error">{errorMessage}</p>-->
{:else}
    <!-- You could add a "Loading" indicator here... -->
{/if}



<style>
    body {
        font-family: sans-serif;

    }

    h2 {
        font-size: 24px;
        margin-bottom: 12px;
    }

    input[type="text"] {
        padding: 8px;
        font-size: 16px;
        border-radius: 4px;
        border: 1px solid #ccc;
        margin-bottom: 12px;
        width: 100%;
        max-width: 400px;
    }

    ul {
        list-style: none;
        margin: 0;
        padding: 0;
    }

    li {
        margin-bottom: 16px;
    }

    h4 {
        font-size: 20px;
        margin-bottom: 4px;
    }

    p {
        margin: 0;
        font-size: 16px;
    }

    strong {
        font-weight: bold;
    }

    .error {
        color: red;
        font-size: 16px;
        margin-top: 12px;
    }

    .weather-card {
        display: flex;
        flex-direction: column;
        align-items: center;
        justify-content: space-between;
        background-color: #fff;
        border-radius: 10px;
        box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
        padding: 20px;
        width: 500px;
    }

    .location {
        text-align: center;
        margin-bottom: 10px;
    }

    .image {
        margin-bottom: 20px;
    }

    .image img {
        border-radius: 10px;
        max-width: 100%;
    }

    .coordinates {
        display: flex;
        flex-direction: column;
        align-items: center;
        justify-content: center;
        margin-bottom: 10px;
        font-size: 14px;
        font-weight: bold;
    }

    .coordinates p {
        margin: 0;
    }

    .coordinates span {
        margin-left: 5px;
    }

    .temperature {
        display: flex;
        flex-direction: column;
        align-items: center;
        justify-content: center;
        margin-bottom: 20px;
    }

    .temperature h1 {
        font-size: 80px;
        margin: 0;
    }

    .min-max-temperature {
        display: flex;
        justify-content: space-between;
        width: 100%;
    }

    .min-max-temperature p {
        margin: 0;
        font-weight: bold;
    }

    .min-max-temperature span {
        margin-left: 5px;
    }

    .conditions {
        background-color: #f4f4f4;
        border-radius: 10px;
        padding: 10px;
        text-align: center;
        font-weight: bold;
        text-transform: uppercase;
        width: 100%;
    }
    .card-container {
        display: flex;
        justify-content: space-between;
        flex-wrap: wrap;
    }

    .weather-card {
        width: 30%;
        margin-bottom: 20px;
        background-color: #fff;
        border-radius: 10px;
        box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
        padding: 20px;
    }

    .weather-card-lonely {
        display: flex;
        flex-direction: column;
        align-items: center;
        justify-content: space-between;
        background-image: linear-gradient(to bottom right, #B3E5FC, #81D4FA);
        border-radius: 10px;
        box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
        padding: 20px;
        width: 500px;
        margin: 0 auto;
        animation: colorChange 5s infinite;
    }
    @keyframes colorChange {
        0% { background-image: linear-gradient(to bottom right, #B3E5FC, #81D4FA); }
        50% { background-image: linear-gradient(to bottom right, #81D4FA, #B3E5FC); }
        100% { background-image: linear-gradient(to bottom right, #B3E5FC, #81D4FA); }
    }


</style>