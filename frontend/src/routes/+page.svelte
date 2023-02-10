<script>
    import {onMount} from "svelte";

    /**
     * @type {any[]}
     */
    let cities = [];
    let region = 'Bratislava';

    onMount(async () => {
        const res = await fetch(`http://localhost:8081/get/`+region);
        cities = await res.json();
    });


    let parameter = '';
    let data = [];
    let errorMessage = '';

    const handleSubmit = async () => {
        try {
            const response = await fetch(`http://localhost:8081/get/${parameter}`);

            // if (!response.ok) {
            //     throw new Error('Network response was not ok');
            // }

            data = await response.json();
            console.log('Successfully fetched data:', data);
        } catch (error) {
            console.error('An error occurred:', error);
            errorMessage = error.message;
        }
    };

</script>

<h2>Search Weather report by region</h2>
<input type="text" bind:value={parameter} on:input={handleSubmit} placeholder="Region" />

{#if data.length}
    <ul>
       <h1>Here is weather report for region {parameter}</h1>
        {#each data as city}
            <h4 class="mb-1 sfw-normal">{city.name}</h4>
            <p class="mb-2">Current temperature: <strong>{city.weather.temp}°C</strong></p>
            <p>Min: <strong>{city.weather.maxTemp}°C</strong></p>
            <p>Max: <strong>{city.weather.minTemp}°C</strong>, Min: <strong>3.89°C</strong></p>
            <p>Feels like: <strong>{city.weather.minTemp}°C</strong>, Min: <strong>3.89°C</strong></p>
        {/each}
    </ul>
{/if}


<section class="vh-100">

</section>
