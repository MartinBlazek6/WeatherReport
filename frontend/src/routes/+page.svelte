<script>
    /**
     * @type {string}
     */

    let parameter = '';
    let cityName = '';
    let citiesData = [];
    let errorMessage = '';

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

    let city = "";
    let data = null;

    async function fetchData() {
        const url = `http://localhost:8081/get/city/${city}`;
        const response = await fetch(url);
        const jsonData = await response.json();
        data = jsonData;}

</script>

<h2>Search Weather report by region</h2>
<input type="text" bind:value={parameter} on:input={handleSubmit} placeholder="Region" />

<h2>Search Weather report by City</h2>
<input type="text" bind:value={city} on:input={fetchData} placeholder="City" />

{#if citiesData.length}
    <ul>
       <h1>Here is weather report for region {parameter}</h1>
        {#each citiesData as city}
            <h4 class="mb-1 sfw-normal">{city.name}</h4>
            <p class="mb-2">Current temperature: <strong>{city.weather.temp}°C</strong></p>
            <p>Min: <strong>{city.weather.minTemp}°C</strong></p>
            <p>Max: <strong>{city.weather.maxTemp}°C</strong></p>
<!--            <p>Feels like: <strong>{city.weather.feelsLike}°C</strong></p> IDK why some cities dont have that-->

        {/each}
    </ul>
{:else}
<!--    <p>Loading</p> for loading -->
{/if}
<div>
    {#if data}
        <h1>Here is weather report for city {data.name}</h1>
        <h4 class="mb-1 sfw-normal">{data.name}</h4>
        <p class="mb-2">Current temperature: <strong>{data.weather.temp}°C</strong></p>
        <p>Min: <strong>{data.weather.minTemp}°C</strong></p>
        <p>Max: <strong>{data.weather.maxTemp}°C</strong></p>
    {/if}
</div>

<section class="vh-100">

</section>
