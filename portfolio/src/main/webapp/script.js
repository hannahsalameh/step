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

function loggedInCheck(){
  fetch('/loginStatus').then(response => response.json()).then(data => {
    var commentDesc = document.getElementById("commentDesc");
    const link = document.createElement("a");
    link.href = data.url;
    link.innerHTML = "here";
    if(data.isLoggedIn){
      document.getElementById("delete").style.display = "block";
      document.getElementById("comment_form").style.display = "inline";
      commentDesc.innerHTML = `Want Hannah to know what you think of her website?
                               Feel free to leave a comment on this page to make
                               sure she sees it! Click `;

      commentDesc.appendChild(link);
      commentDesc.insertAdjacentHTML('beforeend', " to log out. ");
    } else {
      commentDesc.innerHTML = "Wanna post a comment? Click ";
      commentDesc.appendChild(link);
      commentDesc.insertAdjacentHTML('beforeend', " to log in. ");
    }
  })
}

function displayComment(limValue){
    var fetchString = '/comments';
    if(limValue){
        fetchString += "?limit=";
        fetchString += limValue;
    }
    fetch(fetchString).then(response => response.json()).then(data => {
        commentContainer = document.getElementById("comment_container");
        commentContainer.innerHTML = "";
        for(var i = 0; i < data.length; ++i){
            commentContainer.appendChild(
              createComment(data[i].title, data[i].body, data[i].email, data[i].score)
            );
        }
    })
}

function limitNumComments(){
    var limValue = document.getElementById("limit").value;
    displayComment(limValue);
}

function createComment(titleText, bodyText, emailText,sentimentScore){
    const comment = document.createElement("div");
    if(sentimentScore.toFixed(1) > .3){
      comment.className = "comment posComment";
    } else if (sentimentScore.toFixed(1) <=.3 && sentimentScore.toFixed(1) >= -.3) {
      comment.className = "comment neuComment";
    } else {
      comment.className = "comment negComment";
    }

    const title = document.createElement("h3");
    title.className = "commentTitle";
    title.appendChild(document.createTextNode(titleText));
    comment.appendChild(title);

    const email = document.createElement("p");
    email.className = "userEmail";
    email.appendChild(document.createTextNode(emailText));
    comment.appendChild(email);

    const body = document.createElement("p");
    body.className = "commentBody";
    body.appendChild(document.createTextNode(bodyText));
    comment.appendChild(body);

    const score = document.createElement("p");
    score.className = "sentimentScore";
    sentimentText = "Sentiment Score: " + sentimentScore.toFixed(2);
    score.appendChild(document.createTextNode(sentimentText));
    comment.appendChild(score);

    return comment;
}

function deleteComments(){
    var init = {method: 'POST'}
    var request = new Request('/delete-data',init);
    fetch(request).then(() => {
        displayComment(10);
    });
}

function expandFakeBandDesign(){
  var page_heading = "Parasite";
  var page_heading_desc = `Parasite is a fake band created by me and my friends back home.
                              While we have not created any music yet (hence the fake band),
                              I have created plenty of promotional material for us.
                              Hopefully one day the band can become a reality.`;
  var img_ids_desc = [
            [   "fake_band",
                `The first image is of a record I designed while playing with shape language.
                While simple, it utilizes our primary color scheme of red, yellow, and green
                and was really fun to make. Also, the drop shadow makes it really pop. If we
                were a real band, this would probably be the design for our vinyls.`
            ],
            [   "fake_band2",
                `The second image is a mockup of an album cover. The title of the album, Highway to
                Nowhere, is inspired from the classic song Highway to Hell, but with a more indie vibe.
                This cover art mockup is where I solidified the album's color scheme and decided on the
                overall vibe of the art I wanted to create for all subsequent art.`
            ],
            [
                "fake_band3",
                `This final image is actually the first promotional art I made. This image is a mockup of
                a potential band poster that we created with the dream we would play at the Observatory in San Diego.
                This poster is where I decided on the overall shape language of the band, with its trippy,
                abstract shapes and gradiating color scheme. This is also the first place the tiny skull comes up,
                which is an overall symbol for the band Pesticide, similar to how a skull is a typical symbol
                for danger and a toxic substance.`
            ]
  ];
  expandDesign(page_heading,page_heading_desc,img_ids_desc);
}

function expandEASDesign(){
  var page_heading = "Epsilon Alpha Sigma";
  var page_heading_desc = `Epsilon Alpha Sigma is the sorority I am a part of at the University of Michigan. This semester
                            I have taken over the position of apparel chair, and therefore have a lot of new designs.
                            Since we are only allowed to wear our colors (white, purple, and lavender), creating new
                            designs is a challenge. This challenge forces me to make more creative, inventive designs.
                            Epsilon Alpha Sigma is the first and only Arab sorority in the nation and has chapters at
                            UMich, UCLA, UNLV, UC Riverside, and UC Davis.`;
   var img_ids_desc = [
            [
                "eas",
                `This design is my current favorite clothing design I've made. The design is inspired by
                the brand Bad Monday Apparel. This design features a dove and makes use of text manipulation
                to create an appealing, balanced design. `
            ],
            [
                "eas2012",
                `This is actually the first design I made for Epsilon Alpha Sigma. This design features
                our national founding year in arabic. I was inspired by vintage sports jerseys when designing
                the numbers. Also, I chose to use arabic both because of our identity as an Arabic sorority and
                because of the shape language of the arabic numerals allows for more interesting designs than 2012
                would have.`
            ]
   ];
  expandDesign(page_heading,page_heading_desc,img_ids_desc);
}

function expandTraditionalDesign(){
  var page_heading = "Traditional Art";
  var page_heading_desc = `This page is to showcase my favorite traditional art pieces. While I mainly work in traditional art,
            a lot of these pieces are outdated as they were made for my AP Art portfolio. Therefore, I am
            only choosing to showcase my best work. Hopefully, over the summer I will have more time to do traditional
            art and add to this page.`;
  var img_ids_desc = [
            [
                "traditional",
                `This was a piece originally created for my AP Art portfolio. I liked the idea of building up a shape using
                 only simple color schemes and simple shapes. The face was cut off to symbolize the mundaneness of the action;
                  the person doing the act does not matter as much as the act itself. The color scheme was also specifically chosen
                  since the normalicy of the act is not something that should be taken bad, but instead draw mystery to the subject.`
            ],
            [
                "traditional2",
                `This is my most recent pen piece. One of my friends was playing guitar on a snapchat video, and I quickly
                did this piece to commemorate the moment. Again, the face is cut off to show that the person does not matter as
                much as the act. By cropping out the face, you create a mystery that only the artist knows the answer to.`
            ],
            [
                "traditional3",
                `This is my first and biggest pen piece. There is no deeper meaning here, its just a bike laying down
                on its side.`
            ]
        ];
        expandDesign(page_heading,page_heading_desc,img_ids_desc);
    }

function expandMiscDesign(){
  var page_heading = "Miscellaneous Art";
  var page_heading_desc = `This page features all the miscellaneous designs that do not fit in the previous three categories.
                            As a result of their general randomness, most of these designs can be found on my redbubble. Please
                            make sure to check it out if you like any of these designs! Most of these designs were made in either
                            Procreate or Adobe Illustrator.`;
  var img_ids_desc = [
            [
                "misc",
                `This is my first design I made with Adobe Illustrator. It was made by creating a blend and then
                applying filters and effects until I was satisfied with the composition. After creating the intial
                swirl, I applied a slight grain to the whole piece to make it look dated. A lot of my subsequent pieces
                use a similar effect.`
            ],
            [
                "passionFruit",
                `This design was made as a represention of how the song Passionfruit by Drake sounds to me.
                This piece is meant to be worn as a shirt design and while the design was made with procreate, the
                text was done with illustrator.`
            ],
            [
                "face",
                `This design was made as an immitation of Obey Giant's work. I used illustrator in order to make it,
                but it was mainly done by hand, not through effects. This is my first realistic project on illustrator
                and it makes use of my classic color scheme of green and dark red. `
            ]
  ];
 expandDesign(page_heading,page_heading_desc,img_ids_desc);
}

function expandDesign(page_heading,page_heading_desc,img_ids_desc){
    //whenever you click an image on the description page, this pulls up more images in the category alongside descriptions
    const designPage = document.getElementById('design_container');
    designPage.innerHTML = "";

    var button = document.createElement("button");
    button.appendChild(document.createTextNode("Back"));
    button.className = "back_btn";
    button.addEventListener('click', function(){ location.reload();}, false);
    designPage.appendChild(button);

    const title = document.createElement("h1");
    const titleText = document.createTextNode(page_heading);
    title.appendChild(titleText);
    const desc = document.createElement("p");
    desc.className = "main_desc";
    const descText = document.createTextNode(page_heading_desc);
    desc.appendChild(descText);
    designPage.appendChild(title);
    designPage.appendChild(descText);

    var outer_div = document.createElement("div");
    outer_div.className = "column_disp";
    for(var i = 0; i < img_ids_desc.length; ++i){
        var child_div = document.createElement("div");
            child_div.className = "row_disp";
            var img_div = document.createElement("div");
            img_div.className = "expanded_img";
            img_div.id = img_ids_desc[i][0];
            var p_tag = document.createElement("p");
            p_tag.className = "description";
            var descriptionNode = document.createTextNode(img_ids_desc[i][1]);
            p_tag.appendChild(descriptionNode);
            child_div.appendChild(img_div);
            child_div.appendChild(p_tag);
            outer_div.appendChild(child_div);
    }
    designPage.appendChild(outer_div);
}



function addRandomFact(){
    const facts =
    ['Hannah has tried to learn arabic 10 times. She still does not know it.',
    'Hannah was once a girl scout!',
    'Hannah has only ever broken 1 bone, and it was by falling off a swing set.',
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
    'Hannah thinks she\'s funny, but thats debatable.',
    'Hannah\'s favorite singer is Lorde and hopes she comes back soon.',
    'In midde school, Hannah was occasionally confused for Hannah Montana but she doesn\'t know why.',
    'Hannah has a 5 day streak on Duolingo.',
    'Hannah has completed the pokedex twice, once in Pokemon Pearl and once in Pokemon Sun.',
    'Hannah really likes Jet Set Radio, even though she\'s played for 5 years and still hasn\'t finished the game.',
    'Hannah has a very active twitter, but is not funny enough to have followers.',
    'Hannah has owned 6 chickens in her life, despite living in Southern California.',
    'Hannah sometimes gets confused between Miami and Malibu despite living 2 hours from Malibu.',
    'Hannah has met Tony Hawk and the voice actor for Spongebob\'s grandma.',
    'Hannah was at the livetaping of a nickelodeon show, but was so disinterested that the camera never filmed her.',
    'In the last 3 months, Hannah has eaten 13 pounds of m&ms.',
    'Over her entire lifetime, Hannah has had 17 pets: 6 fish, 2 parakeet, 1 guinea pig, 1 bunny, 2 dogs, 6 chickens. Hannah does not live on a farm.'
    ];
    const fact = facts[Math.floor(Math.random()*facts.length)];
    const factContainer = document.getElementById('fact-container');
    factContainer.innerHTML = "";
    var p_tag = document.createElement("p");
    var factTextNode = document.createTextNode(fact);
    p_tag.appendChild(factTextNode);
    factContainer.appendChild(p_tag);
}
