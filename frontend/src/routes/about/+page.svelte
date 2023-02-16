
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

	<form class="form" on:submit|preventDefault={() => {
	  sendString(stringValue)
	    .then(data => {
	      console.log('String sent successfully:', data);
	    });
	}}>
		<input class="form-input" type="text" bind:value={stringValue} placeholder="Enter a city"/>
		<button class="form-button" type="submit">Add City</button>
	</form>

<!--	<button class="weather-button" on:click={handleSubmit}>Get Weather</button>-->

	{#if errorMessage}
		<p class="error-message">{errorMessage}</p>
	{/if}

</div>

<style>
	.form {
		display: flex;
		flex-direction: column;
		align-items: center;
		justify-content: center;
		margin-bottom: 20px;
	}

	.form-input {
		padding: 8px;
		font-size: 16px;
		border-radius: 4px;
		border: 1px solid #ccc;
		margin-bottom: 12px;
		width: 100%;
		max-width: 400px;
	}

	.form-button {
		padding: 8px;
		font-size: 16px;
		border-radius: 4px;
		border: none;
		background-color: #007bff;
		color: #fff;
		width: 100%;
		max-width: 400px;
		cursor: pointer;
	}

	.form-button:hover {
		background-color: #0069d9;
	}

	.weather-button {
		padding: 8px;
		font-size: 16px;
		border-radius: 4px;
		border: none;
		background-color: #007bff;
		color: #fff;
		cursor: pointer;
	}

	.weather-button:hover {
		background-color: #0069d9;
	}

	.error-message {
		color: red;
		font-size: 16px;
		margin-top: 12px;
	}

</style>
