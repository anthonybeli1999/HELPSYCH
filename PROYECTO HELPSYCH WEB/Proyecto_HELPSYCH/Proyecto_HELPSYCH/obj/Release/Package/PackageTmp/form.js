

// Web app's firebase configuration
let firebaseConfig = {
    apiKey: "AIzaSyCCilUA5-Pu1LafwsvbyD3HBchsKE1oqTY",
    authDomain: "helpsych-66739.firebaseapp.com",
    databaseURL: "https://helpsych-66739-default-rtdb.firebaseio.com",
    projectId: "helpsych-66739",
    storageBucket: "helpsych-66739.appspot.com",
    messagingSenderId: "529615864029",
    appId: "1:529615864029:web:58f450812847a0a7a3db34",
};
// Initialize Firebase
firebase.initializeApp(firebaseConfig);

// Create event to database function
function createEvent(dateBooking, roomType, eventName, startTime, endTime) {
    // console.log(counter);
    counter += 1;
    // console.log(counter);
    let event = {
        id: counter,
        date: dateBooking,
        room: roomType,
        event: eventName,
        startTime: startTime,
        endTime: endTime,
    };
    // set event to database
    let db = firebase.database().ref("helpsych-66739-default-rtdb.firebaseio.com" + counter);
    db.set(event);
}

// Read event from database and add event to agenda table
function readEvent() {
    agendaTableRows.innerHTML = "";
    let events = firebase.database().ref("helpsych-66739-default-rtdb.firebaseio.com");
    events.on("child_added", function (data) {
        let eventValue = data.val();

        // set event date
        let dateEvent = new Date(eventValue.date)
            .toLocaleDateString("en-US", {
                weekday: "long",
                year: "numeric",
                month: "long",
                day: "numeric",
            })
            .split(", ");
        // console.log(dateEvent); //["Thursday", "July 9", "2020"]
        let dayOfMonth = dateEvent[1].split(" ")[1];
        let dayOfWeek = dateEvent[0];
        let shortDate = dateEvent[1].split(" ")[0] + ", " + dateEvent[2];

        // add to agenda table
        agendaTableRows.innerHTML += `
        <tr>
            <td class="agenda-date">
                <div class="dayofmonth">${dayOfMonth}</div>
                <div class="dayofweek">${dayOfWeek}</div>
                <div class="shortdate text-muted">${shortDate}</div>
            </td>
            <td class="agenda-time">${eventValue.startTime} - ${eventValue.endTime}</td>
            <td class="agenda-room">${eventValue.room}</td>
            <td>${eventValue.event}</td>
            <td class="agenda-button">
                <button type="button" class="btn btn-warning btn-sm" onclick="updateForm(${eventValue.id}, '${eventValue.date}', '${eventValue.room}', '${eventValue.event}', '${eventValue.startTime}', '${eventValue.endTime}')">
                    <i class="far fa-edit"></i>
                </button>
                <button type="button" class="btn btn-danger btn-sm"
                onclick="deleteEvent(${eventValue.id})">
                    <i class="fas fa-trash"></i>
                </button>
            </td>
        </tr>
        `;
    });
}

// Read event from database and add to agenda table when refresh page
let agendaTableRows = document.getElementById("agenda-table-rows");
agendaTableRows.addEventListener("load", readEvent());

// Create event to database when submit
let formCreate = document.getElementById("myForm");
formCreate.addEventListener("submit", (e) => {
    // get form values
    let dateBooking = document.getElementById("inputDate").value;
    let startHour = document.getElementById("inputStartTimeHour").value;
    let startMinute = document.getElementById("inputStartTimeMinute").value;
    let endHour = document.getElementById("inputEndTimeHour").value;
    let endMinute = document.getElementById("inputEndTimeMinute").value;
    let roomType = document.querySelector('input[name="roomType"]:checked').value;
    let eventName = document.getElementById("textAreaEvent").value;
    // set time format
    let startTime = `${startHour}:${startMinute}`;
    let endTime = `${endHour}:${endMinute}`;

    e.preventDefault();
    // create event to database
    createEvent(dateBooking, roomType, eventName, startTime, endTime);
    // read event from database and add to agenda table
    readEvent();
    formCreate.reset();
});


// Update event to database
function updateEvent(id, dateBooking, roomType, eventName, startTime, endTime) {
    let eventUpdate = {
        id: id,
        date: dateBooking,
        room: roomType,
        event: eventName,
        startTime: startTime,
        endTime: endTime,
    };
    // set event to database
    let db = firebase.database().ref("helpsych-66739-default-rtdb.firebaseio.com" + id);
    db.set(eventUpdate);
}


// Reset form
function reset() {
    let form = document.getElementById("form");
    form.innerHTML = `
      <!-- Start form content -->
      <form
        id="myForm"
        action="#agenda"
        method=""
        autocomplete="on"
        validate
      >
        <!-- Start input date -->
        <div class="form-group">
          <label for="inputDate">Date</label>
          <input
            type="date"
            class="form-control"
            id="inputDate"
            name="date"
            required
          />
        </div>
        <!-- End input date -->
        <!-- Start input start time -->
        <div class="form-group">
          <label>Start Time</label>
          <div
            class="d-flex flex-row justify-content-between align-items-center"
          >
            <select
              class="form-control mr-1"
              id="inputStartTimeHour"
              name="startHour"
              required
            >
              <option value="" disabled selected>Hour</option>
              <option value="08">08</option>
              <option value="09">09</option>
              <option value="10">10</option>
              <option value="11">11</option>
              <option value="12">12</option>
              <option value="13">13</option>
              <option value="14">14</option>
              <option value="15">15</option>
            </select>
            <div class="pl-1 pr-2">:</div>
            <select
              class="form-control"
              id="inputStartTimeMinute"
              name="startMinute"
              required
            >
              <option value="" disabled selected>Min</option>
              <option value="00">00</option>
              <option value="30">30</option>
            </select>
          </div>
        </div>
        <!-- End input start time -->
        <!-- Start input end time -->
        <div class="form-group">
          <label>End Time</label>
          <div
            class="d-flex flex-row justify-content-between align-items-center"
          >
            <select
              class="form-control mr-1"
              id="inputEndTimeHour"
              name="endHour"
              required
            >
              <option value="" disabled selected>Hour</option>
              <option value="09">09</option>
              <option value="10">10</option>
              <option value="11">11</option>
              <option value="12">12</option>
              <option value="13">13</option>
              <option value="14">14</option>
              <option value="15">15</option>
              <option value="16">16</option>
            </select>
            <div class="pl-1 pr-2">:</div>
            <select
              class="form-control"
              id="inputEndTimeMinute"
              name="endMinute"
              required
            >
              <option value="" disabled selected>Min</option>
              <option value="00">00</option>
              <option value="30">30</option>
            </select>
          </div>
        </div>
        <!-- End input end time -->
        <!-- Start check room type -->
        <div class="form-group">
          <legend class="col-form-label pt-0">Choose a Room</legend>
          <div class="form-check form-check-inline">
            <input
              type="radio"
              class="form-check-input"
              id="inlineRadioType1"
              name="roomType"
              value="Room 1"
              required
            />
            <label class="form-check-label" for="inlineRadioType1"
              >Room 1 (10 People)</label
            >
          </div>
          <div class="form-check form-check-inline">
            <input
              type="radio"
              class="form-check-input"
              id="inlineRadioType2"
              name="roomType"
              value="Room 2"
              required
            />
            <label class="form-check-label" for="inlineRadioType2"
              >Room 2 (20 People)</label
            >
          </div>
          <div class="form-check form-check-inline">
            <input
              type="radio"
              class="form-check-input"
              id="inlineRadioType3"
              name="roomType"
              value="Room 3"
              required
            />
            <label class="form-check-label" for="inlineRadioType3"
              >Room 3 (30 People)</label
            >
          </div>
          <div class="form-check form-check-inline">
            <input
              type="radio"
              class="form-check-input"
              id="inlineRadioType4"
              name="roomType"
              value="Room 4"
              required
            />
            <label class="form-check-label" for="inlineRadioType4"
              >Room 4 (40 People)</label
            >
          </div>
        </div>
        <!-- End check room type -->
        <!-- Start input event -->
        <div class="form-group">
          <label for="textAreaEvent">Event</label>
          <textarea
            class="form-control"
            name="event"
            id="textAreaEvent"
            rows="2"
            placeholder="Tell us your event name..."
          ></textarea>
        </div>
        <!-- End input event -->
        <!-- Start submit button -->
        <button
          class="btn btn-primary btn-block"
          type="submit"
          id="btnSubmit"
        ><i class="fas fa-paper-plane"></i>
          Submit
        </button>
        <!-- End submit button -->
      </form>
      <!-- End form content -->
    `;
    let formCreate = document.getElementById("myForm");
    formCreate.addEventListener("submit", (e) => {
        // get form values
        let dateBooking = document.getElementById("inputDate").value;
        let startHour = document.getElementById("inputStartTimeHour").value;
        let startMinute = document.getElementById("inputStartTimeMinute").value;
        let endHour = document.getElementById("inputEndTimeHour").value;
        let endMinute = document.getElementById("inputEndTimeMinute").value;
        let roomType = document.querySelector('input[name="roomType"]:checked')
            .value;
        let eventName = document.getElementById("textAreaEvent").value;
        // set time format
        let startTime = `${startHour}:${startMinute}`;
        let endTime = `${endHour}:${endMinute}`;

        e.preventDefault();
        // create event to database
        createEvent(dateBooking, roomType, eventName, startTime, endTime);
        // read event from database and add to agenda table
        readEvent();
        formCreate.reset();
    });
}



// Update event form and Update event to database and reset form update when form update submit
function updateForm(id, dateBooking, roomType, eventName, startTime, endTime) {
    // set form update
    let form = document.getElementById("form");
    form.innerHTML = `
    <form
    id="myFormUpdate"
    action="#agenda"
    method=""
    autocomplete="on"
    validate
    >
    <!-- Start input date -->
            <div class="form-group">
              <label for="inputDate">Date</label>
              <input
                type="date"
                class="form-control"
                id="inputDate"
                name="date"
                required
              />
            </div>
            <!-- End input date -->
            <!-- Start input start time -->
            <div class="form-group">
              <label>Start Time</label>
              <div
                class="d-flex flex-row justify-content-between align-items-center"
              >
                <select
                  class="form-control mr-1"
                  id="inputStartTimeHour"
                  name="startHour"
                  required
                >
                  <option value="" disabled selected>Hour</option>
                  <option value="08">08</option>
                  <option value="09">09</option>
                  <option value="10">10</option>
                  <option value="11">11</option>
                  <option value="12">12</option>
                  <option value="13">13</option>
                  <option value="14">14</option>
                  <option value="15">15</option>
                </select>
                <div class="pl-1 pr-2">:</div>
                <select
                  class="form-control"
                  id="inputStartTimeMinute"
                  name="startMinute"
                  required
                >
                  <option value="" disabled selected>Min</option>
                  <option value="00">00</option>
                  <option value="30">30</option>
                </select>
              </div>
            </div>
            <!-- End input start time -->
            <!-- Start input end time -->
            <div class="form-group">
              <label>End Time</label>
              <div
                class="d-flex flex-row justify-content-between align-items-center"
              >
                <select
                  class="form-control mr-1"
                  id="inputEndTimeHour"
                  name="endHour"
                  required
                >
                  <option value="" disabled selected>Hour</option>
                  <option value="09">09</option>
                  <option value="10">10</option>
                  <option value="11">11</option>
                  <option value="12">12</option>
                  <option value="13">13</option>
                  <option value="14">14</option>
                  <option value="15">15</option>
                  <option value="16">16</option>
                </select>
                <div class="pl-1 pr-2">:</div>
                <select
                  class="form-control"
                  id="inputEndTimeMinute"
                  name="endMinute"
                  required
                >
                  <option value="" disabled selected>Min</option>
                  <option value="00">00</option>
                  <option value="30">30</option>
                </select>
              </div>
            </div>
            <!-- End input end time -->
            <!-- Start check room type -->
            <div class="form-group">
              <legend class="col-form-label pt-0">Choose a Room</legend>
              <div class="form-check form-check-inline">
                <input
                  type="radio"
                  class="form-check-input"
                  id="inlineRadioType1"
                  name="roomType"
                  value="Room 1"
                  required
                />
                <label class="form-check-label" for="inlineRadioType1"
                  >Room 1 (10 People)</label
                >
              </div>
              <div class="form-check form-check-inline">
                <input
                  type="radio"
                  class="form-check-input"
                  id="inlineRadioType2"
                  name="roomType"
                  value="Room 2"
                  required
                />
                <label class="form-check-label" for="inlineRadioType2"
                  >Room 2 (20 People)</label
                >
              </div>
              <div class="form-check form-check-inline">
                <input
                  type="radio"
                  class="form-check-input"
                  id="inlineRadioType3"
                  name="roomType"
                  value="Room 3"
                  required
                />
                <label class="form-check-label" for="inlineRadioType3"
                  >Room 3 (30 People)</label
                >
              </div>
              <div class="form-check form-check-inline">
                <input
                  type="radio"
                  class="form-check-input"
                  id="inlineRadioType4"
                  name="roomType"
                  value="Room 4"
                  required
                />
                <label class="form-check-label" for="inlineRadioType4"
                  >Room 4 (40 People)</label
                >
              </div>
            </div>
            <!-- End check room type -->
            <!-- Start input event -->
            <div class="form-group">
              <label for="textAreaEvent">Event</label>
              <textarea
                class="form-control"
                name="event"
                id="textAreaEvent"
                rows="2"
                placeholder="Tell us your event name..."
              ></textarea>
            </div>
            <!-- End input event -->
            <!-- Start submit button -->
            <button
              class="btn btn-success btn-block"
              type="submit"
              id="btnUpdate"
            ><i class="fas fa-sync-alt"></i>
              Update
            </button>
            <button
              class="btn btn-danger btn-block"
              type="button"
              id="btnReset"
            ><i class="fas fa-ban"></i>
              Cancel
            </button>
            <!-- End submit button -->
    </form>
    <!-- End form content -->
    `;

    // show existing data in form update
    document.getElementById("inputDate").setAttribute("value", dateBooking);
    document.getElementById("inputStartTimeHour").value = startTime.split(":")[0];
    document.getElementById("inputStartTimeMinute").value = startTime.split(
        ":"
    )[1];
    document.getElementById("inputEndTimeHour").value = endTime.split(":")[0];
    document.getElementById("inputEndTimeMinute").value = endTime.split(":")[1];
    document.querySelector(`input[value="${roomType}"]`).checked = true;
    document.getElementById("textAreaEvent").value = eventName;

    // update database with form update data
    document.getElementById("myFormUpdate").addEventListener("submit", (e) => {
        e.preventDefault();
        // get form update data and update to database
        // get form update values
        let dateBooking = document.getElementById("inputDate").value;
        let startHour = document.getElementById("inputStartTimeHour").value;
        let startMinute = document.getElementById("inputStartTimeMinute").value;
        let endHour = document.getElementById("inputEndTimeHour").value;
        let endMinute = document.getElementById("inputEndTimeMinute").value;
        let roomType = document.querySelector('input[name="roomType"]:checked')
            .value;
        let eventName = document.getElementById("textAreaEvent").value;
        // set time format
        let startTime = `${startHour}:${startMinute}`;
        let endTime = `${endHour}:${endMinute}`;

        // update event to database
        updateEvent(id, dateBooking, roomType, eventName, startTime, endTime);

        readEvent();
        reset();
    });
    // reset form update
    document.getElementById("btnReset").addEventListener("click", (e) => {
        reset();
    });
}



// Delete event in database and update event to agenda table
function deleteEvent(id) {
    // detlet event from database
    let event = firebase.database().ref("helpsych-66739-default-rtdb.firebaseio.com" + id);
    event.remove();

    readEvent();
}