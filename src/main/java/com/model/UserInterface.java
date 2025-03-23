package com.model;

public class UserInterface {
    public static void main(String[] args) {
        teacherSignUpScenario();
    }

    public static void teacherSignUpScenario(){
        MusicSystemFACADE system = MusicSystemFACADE.getInstance();
        String firstName = "Valerie";
        String lastName = "Frizzle";
        String email = "valeriefrizzle@yahoo.com";
        String username = "MzFrizz";
        String password = "abc123";
        System.out.println("Attempting to sign up as " + username + "...");
        if(system.signUp(true, firstName, lastName, email, username, password))
            System.out.println("Sign-up successful!");
        else
            System.out.println("Sign-up failed.");

        System.out.println("Attempting to create a class...");
        int classNumber = system.createClass();
        if(classNumber >= 0)
            System.out.println("Successfully created class #" + classNumber + "!");
        else
            System.out.println("Class creation failed.");
        System.out.println("Attempting to add Students to class...");
        system.addToClass(
                classNumber,
                system.fetchUser("dbreci"),
                system.fetchUser("ctferg")
        );
        System.out.println("Printing roster for class #" + classNumber + "...");
        for(User user : system.getRoster(classNumber))
            System.out.println("\t" + user.getUsername());
        String lessonName = "Learning Songs From Vide Games: 'Alphys' by Toby Fox";
        System.out.println("Attempting to create a lesson \"" + lessonName + "\"...");
        Lesson firstLesson = system.createLesson(lessonName); // Doesn't work yet because LessonList isn't done
        firstLesson.addSong(system.getSong("Alphys"));
        System.out.println("Attempting to assign lesson to class #" + classNumber + "...");
        system.assignLesson(classNumber, firstLesson);
        for(User user : system.getRoster(classNumber)){
            System.out.println("\t" + user.getUsername() + "'s assigned lessons:");
            for(Lesson lesson : user.getAssignedLessons())
                System.out.println(lesson.getTitle());
        }
        System.out.println("Logging out now");
        system.logout();
    }
}
