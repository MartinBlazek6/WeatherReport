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

</script>

<!--<input type="text" bind:value={city} placeholder="City" />-->
<!--<input type="text" bind:value={countryCode} placeholder="SK" />-->
<!--<button on:click={handleSubmit}>Submit</button>-->
<!--{#if errorMessage}-->
<!--    <p style="color: red">{errorMessage}</p>-->
<!--{/if}-->


<!--<form on:submit|preventDefault={() => {-->
<!--  sendString(stringValue)-->
<!--    .then(data => {-->
<!--      console.log('String sent successfully:', data);-->
<!--    });-->
<!--}}>-->
<!--    <input type="text" bind:value={stringValue}/>-->
<!--    <button type="submit">Add City</button>-->
<!--</form>-->
<!--<br>-->

<!--<form on:submit|preventDefault={() => {-->
<!--  sendCityUpdateData(old, newN, reg, pop)-->
<!--    .then(data => {-->
<!--      console.log('Form data sent successfully:', data);-->
<!--    });-->
<!--}}>-->
<!--    <input type="text" bind:value={old} placeholder="City Name"/>-->
<!--    <input type="text" bind:value={newN} placeholder="New Name"/>-->
<!--    <input type="text" bind:value={reg} placeholder="add Region"/>-->
<!--    <input type="text" bind:value={pop} placeholder="set population"/>-->
<!--    <button type="submit">Update City</button>-->
<!--</form>-->

<section class="vh-100">
    <div class="container py-5 h-100">

        <div class="row d-flex justify-content-center align-items-center h-100">
            <div class="col-md-8 col-lg-6 col-xl-4">

                <h3 class="mb-4 pb-2 fw-normal">Check the weather forecast in region</h3>

                <div class="input-group rounded mb-3">
                    <input type="search" class="form-control rounded" placeholder="Zilina" aria-label="Search"
                           aria-describedby="search-addon"/>
                    <input type="text" class="form-control rounded" placeholder="SK" aria-label="Search"
                           aria-describedby="search-addon"/>
                    <a href="#!" type="submit">
            <span class="input-group-text border-0 fw-bold" id="search-addon">
              Check!
            </span>
                    </a>
                </div>
                {#each cities as city}

                    <div class="card shadow-0 border">
                        <div class="card-body p-4">
<!--                            v-model-->
<!--                           linkedinlearning-->
                            <h4 class="mb-1 sfw-normal">{city.name}</h4>
                            <p class="mb-2">Current temperature: <strong>{city.weather.temp}°C</strong></p>
                            <p>Min: <strong>{city.weather.maxTemp}°C</strong></p>
                            <p>Max: <strong>{city.weather.minTemp}°C</strong>, Min: <strong>3.89°C</strong></p>

                            <div class="d-flex flex-row align-items-center">
                                <p class="mb-0 me-4">Scattered Clouds</p>
                                <i class="fas fa-cloud fa-3x" style="color: #eee;"></i>
                            </div>

                        </div>
                    </div>
                {:else}
                    <!-- this block renders when cities.length === 0 -->
                    <p>loading...</p>
                {/each}
            </div>
        </div>

    </div>
</section>
