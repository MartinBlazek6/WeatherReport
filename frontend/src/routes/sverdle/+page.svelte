<script lang="ts">
	import { confetti } from '@neoconfetti/svelte';
	import { enhance } from '$app/forms';
	import type { PageData, ActionData } from './$types';
	import { reduced_motion } from './reduced-motion';

	export let data: PageData;

	export let form: ActionData;

	/** Whether or not the user has won */
	$: won = data.answers.at(-1) === 'xxxxx';

	/** The index of the current guess */
	$: i = won ? -1 : data.answers.length;

	/** Whether the current guess can be submitted */
	$: submittable = data.guesses[i]?.length === 5;

	/**
	 * A map of classnames for all letters that have been guessed,
	 * used for styling the keyboard
	 */
	let classnames: Record<string, 'exact' | 'close' | 'missing'>;

	/**
	 * A map of descriptions for all letters that have been guessed,
	 * used for adding text for assistive technology (e.g. screen readers)
	 */
	let description: Record<string, string>;

	$: {
		classnames = {};
		description = {};

		data.answers.forEach((answer, i) => {
			const guess = data.guesses[i];

			for (let i = 0; i < 5; i += 1) {
				const letter = guess[i];

				if (answer[i] === 'x') {
					classnames[letter] = 'exact';
					description[letter] = 'correct';
				} else if (!classnames[letter]) {
					classnames[letter] = answer[i] === 'c' ? 'close' : 'missing';
					description[letter] = answer[i] === 'c' ? 'present' : 'absent';
				}
			}
		});
	}

	/**
	 * Modify the game state without making a trip to the server,
	 * if client-side JavaScript is enabled
	 */
	function update(event: MouseEvent) {
		const guess = data.guesses[i];
		const key = (event.target as HTMLButtonElement).getAttribute(
			'data-key'
		);

		if (key === 'backspace') {
			data.guesses[i] = guess.slice(0, -1);
			if (form?.badGuess) form.badGuess = false;
		} else if (guess.length < 5) {
			data.guesses[i] += key;
		}
	}

	/**
	 * Trigger form logic in response to a keydown event, so that
	 * desktop users can use the keyboard to play the game
	 */
	function keydown(event: KeyboardEvent) {
		if (event.metaKey) return;

		document
			.querySelector(`[data-key="${event.key}" i]`)
			?.dispatchEvent(new MouseEvent('click', { cancelable: true }));
	}

	let old, newN, reg, pop

	async function sendCityUpdateData(old, newN, reg, pop) {
		try {
			const response = await fetch("http://localhost:8081/update", {
				method: 'POST',
				headers: {
					'Content-Type': 'application/json'
				},
				body: JSON.stringify({old, newN, reg, pop})
			});
			return await response.json();
		} catch (error) {
			console.error('Error sending form data:', error);
		}
	}
</script>



<div class="card">
	<form class="form" on:submit|preventDefault={() => {
    sendCityUpdateData(old, newN, reg, pop)
      .then(data => {
        console.log('Form data sent successfully:', data);
      });
  }}>
		<input class="form-input" type="text" bind:value={old} placeholder="City Name"/>
		<input class="form-input" type="text" bind:value={newN} placeholder="New Name"/>
		<input class="form-input" type="text" bind:value={reg} placeholder="add Region"/>
		<input class="form-input" type="text" bind:value={pop} placeholder="set population"/>
		<button class="form-button" type="submit">Update City</button>
	</form>
</div>


<style>
	.form {
		display: flex;
		flex-wrap: wrap;
		justify-content: center;
	}

	.form-input {
		font-size: 18px;
		padding: 10px;
		margin: 5px;
		border-radius: 5px;
		border: 2px solid #333;
		outline: none;
	}

	.form-button {
		font-size: 18px;
		padding: 10px;
		margin: 5px;
		border-radius: 5px;
		background-color: #007bff;
		color: white;
		border: none;
		cursor: pointer;
	}

	.form-button:hover {
		background-color: #0069d9;
	}

	.form-button:active {
		background-color: #3e8e41;
		transform: translateY(1px);
	}

</style>
