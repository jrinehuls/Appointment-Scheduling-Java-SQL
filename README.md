# Appointment Scheduling Application using JavaFX and SQL
<ul>
  <li>Title: Appointment Scheduling Application</li>
<li>Purpose: The purpose of this application is to add customers and schedule appointments for them for purposes of
    keeping business organized. The customers and appointments can also be modified.</li>
<li>Author: Justin Rinehuls</li>
<li>Contact email: jrinehuls@gmail.com</li>
<li>Application version: 1.0</li>
<li>Date: 08/24/2022</li>
<li>IDE version: IntelliJ IDEA Community Edition 2022.2</li>
<li>JDK version: JDK-17.0.4</li>
<li>JavaFX version: JavaFX-SDK-18.0.1 (In repository, add whole lib folder to libraries.)</li>
<li>MySQL connector driver version: mysql-connector-java-8.0.30 (In repository)</li>
</ul>

<b>What else you need:</b>
<p>You need to have MySQL and create a new user named: sqlUser with a password: passw0rd! and a host
name of either 127.0.0.1 or localhost. create a database and name it client_schedule and open a new query tab.
copy from C195_db_ddl and paste and execute to create tables and populate with data. Since this project has 
appointments that made to be shown in the system timezone, SQL had to be changed to UTC time. Go to
"C:\ProgramData\MySQL\MySQL Server 8.0\my.ini" scroll to SERVER SECTION and find the line [mysqld] enter a line
below that and type: default-time-zone="+00:00" and save.</p>

<b>Directions for how to run the program:</b>
<p>Run the program in the IDE to launch the application. Log in using a correct
username and password combination and click the login button. This brings you to the main screen. A pop-up lets
the user know if there is an appointment associated with that user upcoming within the next 15 minutes. Click ok
to close it.</p>
    
<p>In the customers pane, click the Add Customer button. This takes you to the Add Customer Form. Fill out all
    fields and click Save to add the customer. If there are errors, the user interface will let you know what needs to
    be corrected in order to successfully add and save that customer. You can also click Cancel to return to the main
    screen without adding a customer. To modify a customer, select a customer from the table and click Modify Customer.
    Here you can make changes to that customer and click Save to save the changes. If there are errors, the user
    interface will let you know what needs to be corrected in order to successfully modify and save that customer.
    You can also click Cancel to return to the main screen without modifying the customer. To delete a customer, select
    a customer from the table and click Delete Customer. A pop-up will let you know that the customer and their
    appointments will be deleted. Click ok to confirm and another pop-up will let you know they were deleted. Click ok
    on that to close it. If you decide not to delete during the first pop-up, click cancel.</p>
    
<p>In the appointments pane, click the Add Appointment button. This takes you to the Add Appointment Form. Fill out
    all fields and click Save to add the appointment. If there are errors, the user interface will let you know what
    needs to be corrected in order to successfully add and save that appointment. You can also click Cancel to return to
    the main screen without adding an appointment. To modify an appointment, select an appointment from the table and
    click Modify Appointment. Here you can make changes to that appointment and click Save to save the changes. If there
    are errors, the user interface will let you know what needs to be corrected in order to successfully modify and save
    that appointment. You can also click Cancel to return to the main screen without modifying the appointment. To
    delete an appointment, select an appointment from the table and click Delete Appointment. A pop-up will let you know
    that the appointment will be deleted. Click ok to confirm and another pop-up will let you know the appointment was
    deleted and display the ID and type of appointment. Click ok on that to close it. If you decide not to delete during
    the first pop-up, click cancel.</p>
    
<p>Below the appointments pane there are 3 radio buttons. All appointments is selected by default and shows all
    appointments. If you select this month's appointments, only appointments for the current month are shown. If you
    select this week's appointments, only appointments for the current week are shown.</p>
    
<p>There is also a Reports button. Click on that to go to the Reports Screen. Here there are 3 reports to view.
    First is Appointments by Month and Type. There are two combo boxes. One for selecting a month and one for selecting
    a type. A label displays the total amount of appointments that meet both of those criteria. Second is Appointments
    by contact. This has a combo box for selecting a contact. Upon selecting a contact, the table will populate with all
    appointments associated with that contact. The third report shows all appointments that have end times that are
    earlier than the current local time. These appointments are all in the past and are displayed in a TableView. CLick
    on the Exit to Main button to return to the main screen. To close the application, click on the X in the top right
    corner.</p>
