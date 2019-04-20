# SuperMarioPartyDice

This project is for recreating the boards and character dice of Super Mario Party, and running simulations on those boards to learn all kinds of fun statistical things about the game such as:
* Min, average, and max coins gained during any single game.
* Min, average, and max distance gone during any single game.
* Min, average, and max stars gained during any single game.
* The average place (1st, 2nd, etc) gained at the end of all the games.
* All of the above per the number of allies gained during that game.
* How frequently that number of allies was able to be gained.
* Min, average, and max of which turn each amount of allies was able to be gained.
* All of the above per character dice.
* All of the above per game board.

These simulations are run for each of the four game boards since one of the main questions is how each character die ends up impacting which spaces on each board you're more likely to land on, and how that goes on to affect everything else.

Not sure it's the greatest design, lol. Contrary to what may seem fine at the time, the hours between 8 PM and midnight are not the optimal times to be programming and bashing away at these kinds of simulations, lol.

## Why do this?

Because I'm a giant nerd, lol.

For real though, with the introduction of the unique character dice in SMP, this now means that which character you choose to play has could potentially have very interesting consequences for all of the above pieces of data. This project is, at its core, to determine how significant the different character dice can impact all of this. Maybe even to the point of significantly impacting your odds of winning, and I find this an incredibly fascinating series of questions to try and answer.

## Current State

The current version of these simulations is much more limited than the real SMP games due to complexity and setting more incremental, obtainable goals for myself. With this current state (plus any current goals/questions addressed), I believe that's at least a really good pausing place that still yields really good results that a SMP player could still use.

 * The stars work....mostly.
    * Stars are on the board and work as you would expect. The only issue is that the agent currently doesn't care about them. Getting to a star space and buying the star is pure coincidence, as the agent is choosing which path to take randomly.
 * Character dice only. 
   * The agents only ever use the character die that they have from the beginning, though yes, in a real game a player can choose between their own character die, the character dice of any allies they have, and a standard d6. For now, this project is really about answering how effective each character die is on their own, so we can leave adding this choice for a later version of this. 
   * Though yes, that could end up changing things, as maybe even the stand d6 compliments some character dice far better than others.
   * Another reason to hold off on this is that I'm really worried that this will introduce even more variables. It seems likely to me that different character dice are actually better or worse vs different other character dice, and I can't handle even thinking about that right now, lol.
 * All four players
   * While each simulation is centered on testing one main player, all four characters in a typical Mario Party game are running around the board, collecting coins and stars using the same strategies.
   * Who the other three characters are for each game is chosen randomly, so the resulting statistics say how each character does generically against all characters.
 * No mini-games.
   * This entirely comes down to the skill of the human player. Sure, I could code something up where like, 3/4 of the time the agent wins or something, but.....eh? It sounds reasonable to me to say that any coins you get in mini-games are bonus coins on top of what these simulations say.
   * I have discovered the chances that control how the mini-game teams are formed, so maybe this will change in the future.
 
## Current Goals/Questions

- [x] Take advantage of the capabilities I get with JGraphT to create a visualization of the board graphs.
- [ ] Fine-tune the Sand Bridge collapsing in Megafruit Paradise. I've heard it can randomly collapse after 3-5 uses, but in the four cases I've witnessed it's been 5. Either that's coincidence, or it's not 3-5, or the randomness isn't uniform. Without more data I don't know which it is for certain.
    * Partially done. I did find video evidence of the bridge collapsing after four crossings, and coded that in. That really messed up Diddy and Wario's ability to gain allies, lol.
- [x] Part 1 of implementing the stars: Get them on the board and working.
- [x] ~~Part 2 of implementing the stars: Add some percentage chance that another player got to the star before the computer did.~~ No need because I implemented all four players!
- [ ] Part 3 of implementing the stars: Make the simulations smarter by having the computer choose the paths that are the shortest path to the star.
- [x] Implement all four places, and tracking what place they're always all in.
- [x] Any performance gains I can find in the simulations. It's slower than I'd like.
    * This is really a continuous task that I should always check, but at least for now I've done everything I can.
- [x] Implement all of the rest of the non-item Lucky and Bad Luck events now that all four characters are there.
- [x] Implement the Lakitu and VS spaces. Using random chance to decide who wins VS games.
- [ ] Implement the logic that chooses what type of teams are created for the minigames.
 
 ## Future Goals
 
 These are goals that are a long way off, and would be done as a proper next version rather than any time soon.
 
 * Implement an AI.
   * With the stars in place, that gives the AI the goal of what they need to gather, and if it'd be great to make a proper learning agent so that it can figure out on its own just how important coins, allies, etc are towards the goal of gathering the most stars.
   * In some games, like Super Smash Bros, this is ends up being a lot waiting for the agents to train, because each character is different enough that you can't simply train an agent once, and expect it to do well for other characters. I don't believe that's the case here, as the strategies and tools available to you don't really change, only what spaces in front of you you're likely to land on based on your dice.
 * Extend the AI to choose which dice to use.
   * Phase 1 can be being able to choose between their character dice and the d6.
   * Phase 2 would be also having access to their ally dice they could use.
