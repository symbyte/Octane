package edu.uco.schambers4.octane.DataAccessObjects;

import java.util.ArrayList;

import edu.uco.schambers4.octane.Models.Workout;

/**
 * Created by gordon on 10/25/15.
 */
public interface WorkoutRepository {
    /* read functions */
    Workout getWorkout(String name);
    ArrayList<Workout> getAllWorkouts();

    /* insert and update functions */
    void saveWorkout(Workout workout);
    void saveWorkouts(ArrayList<Workout> workouts);

    /* delete functions */
    void deleteWorkout(Workout workout);

}