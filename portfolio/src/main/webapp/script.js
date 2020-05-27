// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

/**
 * Adds a random greeting to the page.
 */
function addRandomGreeting() {
  const greetings =
      ['Hello world!', '¡Hola Mundo!', '你好，世界！', 'Bonjour le monde!'];

  // Pick a random greeting.
  const greeting = greetings[Math.floor(Math.random() * greetings.length)];

  // Add it to the page.
  const greetingContainer = document.getElementById('greeting-container');
  greetingContainer.innerText = greeting;
}
function addRandomFact(){
    const facts = 
    ['Hannah has tried to learn arabic 10 times. She still does not know it.',
    'Hannah was once a girl scout!',
    'Hannah has broken 1 bone and it from falling off a swing set.',
    'Hannah has 1 sibling: a younger sister named Emily.',
    'Hannah has played almost every pokemon game up onto USUM.',
    'Hannah can not get goosebumps and is not quite sure why not.',
    'Hannah\'s go to starbucks drink is a Mocha Latte',
    'At one point, Hannah had 6 pets at once (no fish!)',
    'Hannah\'s current favorite music genre is indie',
    'Hannah\'s dream vacation is a trip to Peru.',
    'Hannah spends 95% of her time on Tiktok (hamanthasalami).',
    'Hannah is genetically predispositioned to have more bug bites than average.',
    'Hannah is a fan of 100 gecs.',
    'Hannah\'s first concert was Beyonce.',
    'Hannah was NOT an ugly baby, but she was an ugly middle schooler.',
    'Hannah has 10 fingers and 10 toes.',
    'Hannah can trace her ancestory back to 1521. None of her ancestors are historically relevant, though.',
    'Hannah has been to more countries than states (despite living here her whole life).',
    'At one point, Hannah spoke passable Mandarin. That point is not now, sadly.',
    'Hannah thinks she\'s funny, but thats debatable',
    'Hannah\'s favorite singer is Lorde and hopes she comes back soon',
    'In midde school, Hannah was occasionally confused for Hannah Montana but she doesn\'t know why.',
    'Hannah has a 5 day streak on Duolingo',
    'Hannah has completed the pokedex twice, once in Pokemon Pearl and once in Pokemon Sun.',
    'Hannah really likes Jet Set Radio, even though she\'s played for 5 years and still hasn\'t finished the game.',
    'Hannah has a very active twitter, but is not funny enough to have followers.',
    'Hannah has owned 6 chickens in her life, despite living in Southern California.',
    'Hannah sometimes gets confused between Miami and Malibu despite living 2 hours from Malibu.',
    'Hannah has met Tony Hawk and the voice actor for Spongebob\'s grandma.',
    'Hannah was at the livetaping of a nickelodeon show, but was so disinterested that the camera never filmed her.',
    'In the last 3 months, Hannah has eaten 13 pounds of m&ms'
    ];
    const fact = facts[Math.floor(Math.random()*facts.length)];
    const factContainer = document.getElementById('fact-container');
    factContainer.innerHTML = "";
    var tag = document.createElement("p");
    var factTxt = document.createTextNode(fact);
    tag.appendChild(factTxt);
    factContainer.appendChild(tag);
}