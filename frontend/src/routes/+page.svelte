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

{#if citiesData.length}
    <ul>
        <h3>Weather report for region {parameter}</h3>
        {#each citiesData as city}
            <li>
                <h4>{city.name}</h4>
                <p>Current temperature: <strong>{city.weather.temp}°C</strong></p>
                <p>Min: <strong>{city.weather.minTemp}°C</strong></p>
                <p>Max: <strong>{city.weather.maxTemp}°C</strong></p>
            </li>
        {/each}
    </ul>
{:else if errorMessage}
<!--    <p class="error">{errorMessage}</p>-->
{:else}
    <!-- You could add a "Loading" indicator here... -->
{/if}

{#if data}
    <div>
        <h3>Weather report for city {data.name}</h3>
        <h4>{data.name}</h4>
        <p>Current temperature: <strong>{data.weather.temp}°C</strong></p>
        <p>Min: <strong>{data.weather.minTemp}°C</strong></p>
        <p>Max: <strong>{data.weather.maxTemp}°C</strong></p>
        <p>Longitude: <strong>{data.weather.longitude}</strong></p>
        <p>Latitude: <strong>{data.weather.latitude}</strong></p>
    </div>
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
</style>