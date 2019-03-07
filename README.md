# SuperMarioPartyDice

This project is for recreating the boards and character dice of Super Mario Party, and running simulations on those boards to learn all kinds of fun statistical things about the game such as:
* Min, average, and max coins gained during any single game.
* Min, average, and max distance gone during any single game.
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

 * No stars. 
    * The agents have no concept of what goals they might have are, and just pick which path they take randomly. While it is true that star spaces don't count against your movement, and therefore could shift the spaces an agent lands on for the rest of the game, this really only affects two players in a game: The player who gets the star, and the player who is ahead of the current star and behind the next star. This is minimal enough that I think we can still get good results without them.
 * Character dice only. 
   * The agents only ever use the character die that they have from the beginning, though yes, in a real game a player can choose between their own character die, the character dice of any allies they have, and a standard d6. For now, this project is really about answering how effective each character die is on their own, so we can leave adding this choice for a later version of this. 
   * Though yes, that could end up changing things, as maybe even the stand d6 compliments some character dice far better than others.
   * Another reason to hold off on this is that I'm really worried that this will introduce even more variables. It seems likely to me that different character dice are actually better or worse vs different other character dice, and I can't handle even thinking about that right now, lol.
 * Only one player. 
   * I have not implemented all four players running around the board simultaneously. Therefore, anything that involves the players interacting with each other, such as teleporting to another player, is not implemented.
   * As far as giving and taking coins (such as by landing on Lucky and Bad Luck spaces), I'm very comfortable with not implementing these yet, as I have a very strong suspicion that anything like this that involves the coins staying within the "system" of the four players largely evens out in the statistics in the end.
 * No mini-games.
   * This entirely comes down to the skill of the human player. Sure, I could code something up where like, 3/4 of the time the agent wins or something, but.....eh? It sounds reasonable to me to say that any coins you get in mini-games are bonus coins on top of what these simulations say.
   * Also, figuring out which type of mini-game, and therefore what spread of coins you get, depends on what spaces everyone lands on; therefore, I would need to implement all four players first. Again, I could do random, but....eh. I don't feel confident enough that I'd get even close to the real odds.
 
## Current Goals/Questions

- [x] Oh. Right. Character shouldn't be allowed to have less than zero coins. That will probably noticeably increase everyone's coin averages, lol. 
- [x] Implement the "Whomps on the Move" event in the Whomp's Domino Ruins board.
- [x] After addressing the above two issues, we need a look-ahead system for the paths that have some sort of coin toll. That way an agent knows that they can't take a path if they can't pay the toll.
- [x] Get any data on what events come up for Extra Bad Luck spaces (though I have a feeling landing on them happens infrequently enough that it won't really affect the data in the end).
- [x] Take advantage of the capabilities I get with JGraphT to create a visualization of the board graphs.
- [ ] Fine-tune the Sand Bridge collapsing in Megafruit Paradise. I've heard it can randomly collapse after 3-5 uses, but in the four cases I've witnessed it's been 5. Either that's coincidence, or it's not 3-5, or the randomness isn't uniform. Without more data I don't know which it is for certain.
    * Partially done. I did find video evidence of the bridge collapsing after four crossings, and coded that in. That really messed up Diddy and Wario's ability to gain allies, lol.

## Ongoing Goals

These are goals that don't necessarily have a time when they're done, and I'll always appreciate more data/precision on these.

 * Gather data on what events come up when landing on a Lucky Space.
   * Done! Found the actual original probability tables.
 * Gather data on what events come up when landing on a Bad Luck Space (only non-Kamek boards since Kamek is different).
   * Done! Found the actual original probability tables.
 
 ## Future Goals
 
 These are goals that are a long way off, and would be done as a proper next version rather than any time soon.
 
 * Implement the stars.
   * Easy enough to implement these once I know the full list of possible spaces on each board that a star could show up on.
   * Also include implementing some percentage chance that another player gets the star on any given turn.
 * Implement an AI.
   * With the stars in place, that gives the AI the goal of what they need to gather, and if it'd be great to make a proper learning agent so that it can figure out on its own just how important coins, allies, etc are towards the goal of gathering the most stars.
   * In some games, like Super Smash Bros, this is ends up being a lot waiting for the agents to train, because each character is different enough that you can't simply train an agent once, and expect it to do well for other characters. I don't believe that's the case here, as the strategies and tools available to you don't really change, only what spaces in front of you you're likely to land on based on your dice.
 * Extend the AI to choose which dice to use.
   * Phase 1 can be being able to choose between their character dice and the d6.
   * Phase 2 would be also having access to their ally dice they could use.
