'use strict';

// Initialize Firebase
const config = {
  apiKey: "AIzaSyCCilUA5-Pu1LafwsvbyD3HBchsKE1oqTY",
  authDomain: "helpsych-66739.firebaseapp.com",
  databaseURL: "https://helpsych-66739-default-rtdb.firebaseio.com",
  projectId: "helpsych-66739",
  storageBucket: "helpsych-66739.appspot.com",
  messagingSenderId: "529615864029"
};
firebase.initializeApp(config);

// Get a reference to the database service
const database = firebase.database();

// Initial load data
readUserData();

// Shortcuts to DOM Elements.
const $nameInput = document.getElementById('name');
const $emailInput = document.getElementById('phone');
const $ageInput = document.getElementById('sex');
const $usersList = document.getElementById('usersList');

/**
* @description  save object User from document form


/**
* @description  write object User in firebase
*/


/**
* @description  read realtime from firebase
*/
function readUserData() {
  const users = database.ref('Users/');
  users.on('value', function (snapshot) {
    refreshUI(snapshot.val());
  });
}






function refreshUI(users) {
  let tBody = '';
  Object.keys(users).forEach(function (key) {
    const tRow =
      `
      <tr>
        <th scope="row">${key}</th>
        <td>${users[key].name}</td>
        <td>${users[key].phone}</td>
        <td>${users[key].sex}</td>
        <td><a href="/link/to/page2">Continue</a><td>
      </tr>
    `;
    tBody += tRow;
  });

  $usersList.innerHTML = tBody;
};


