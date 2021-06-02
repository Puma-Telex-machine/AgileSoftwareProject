# Puma Telex Machine - AgileSoftwareProject

This is the repository of the group Puma-Telex Machine for the course DAT257 "Agile software project management" at the Chalmers University of Technology.

The project is a Java application for the creation of UML class diagrams which focuses on being easy to use for those unused to UML diagrams. 
This is done by automating certain functions that other applications and services expect users to perform manually, such as the selecting the paths taken by relationship arrows.
The project has been built in java 16.

## Who's who

FHanberg: Filip Hanberg

Frixxo/Tau: Joel Olausson

ThePussyCat/F-Klav: Emil Ekroth

961028/Elmo: Emil Holmsten

Madeleine/Maddzenx/Smäq: Madeleine Xia

lukasmartinsson: Lukas Martinsson

adam2524: Adam Sandén

(Some team members have worked primarily in a group of two or three, so commits are not entirely accurate to actual work)

## Links

[Trello](https://trello.com/b/TAGrxXV9/user-stories)

[Google Drive](https://drive.google.com/drive/folders/1JIiZDm9gnMXk-JVZC-ETI-m7_td6UXiA?usp=sharing)

## Usage

The application can be ran by using the Executable Jar File "AgileSoftwareProject.jar" which is present at the top of the project.

The menus at the left of the screen allows the user to load diagram files, add new boxes to the program, and load in template files, respectively.

Files are saved automatically after major changes, but they can also be saved manually (allowing for the file's name to be changed) using the "Save diagram" button and following
prompt in the file menu. (If this button is not visible, try minimizing the application.) This also allows the files to be saved as templates.

All files created in the program are saved in the "diagrams" and "templates" folders, and can be edited using a text editor.

By right-clicking on the "canvas", a context menu can be opened containing some of the program's major features.

When a box has been created, the two '+'-signs can be pressed to add a method/variable to the box (to correctly add a parameter to a method you'll have to press Enter
after you are done writing. That's just how it works.).

A number of dots will be visible after hovering over a box. Pressing one of these and then pressing a dot on another box will create a relation between the two.
The relation can be edited by left-clicking on it.

Multiple boxes can be selected (and then moved/deleted) by dragging the mouse whilst holding left- or right-click.

The camera can be moved by holding down the middle mouse button/scrollwheel and then dragging.

The program also features keyboard commands for certain actions, namely:

<ul>
<li>Ctrl+A: Select all boxes</li>

<li>Shift+left-click: Add clicked box to multiselection</li>

<li>Delete: Delete selected boxes</li>

<li>Ctrl+C: Copy</li>

<li>Ctrl+X: Clip</li>

<li>Ctrl+V: Paste</li>

<li>Ctrl+D: Duplicate (instant Copy+Paste)</li>

<li>Ctrl+Z: Undo</li>

<li>Ctrl+Y: Redo</li>

<li>M+left-click+drag: Move the camera</li>
</ul>
