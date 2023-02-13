
<script>

	let stringValue;
	async function sendString(string) {
		try {
			const response = await fetch('http://localhost:8081/add', {
				method: 'POST',
				headers: {
					'Content-Type': 'application/json'
				},
				body: JSON.stringify({string})
			});
			return await response.json();
		} catch (error) {
			console.error('Error sending string:', error);
		}
	}
	let city,errorMessage
	let countryCode = 'SK'


	const handleSubmit = async () => {
		const data = {
			city,
			countryCode
		};

		try {
			const response = await fetch('http://localhost:8081/weather', {
				method: 'POST',
				headers: {
					'Content-Type': 'application/json'
				},
				body: JSON.stringify(data)
			});

			if (!response.ok) {
				throw new Error('Network response was not ok');
			}
			console.log('Successfully sent data:', JSON.stringify(data));
		} catch (error) {
			console.error('An error occurred:', error);
			errorMessage = error.message;
		}
	};
</script>
<svelte:head>
	<title>About</title>
	<meta name="description" content="About this app" />
</svelte:head>

<div class="text-column">

	<form on:submit|preventDefault={() => {
  sendString(stringValue)
    .then(data => {
      console.log('String sent successfully:', data);
    });
}}>
		<input type="text" bind:value={stringValue}/>
		<button type="submit">Add City</button>
	</form>

<!--	<input type="text" bind:value={countryCode} placeholder="SK" />-->
	<button on:click={handleSubmit}>Get Weather</button>
	{#if errorMessage}
		<p style="color: red">{errorMessage}</p>
	{/if}
</div>
