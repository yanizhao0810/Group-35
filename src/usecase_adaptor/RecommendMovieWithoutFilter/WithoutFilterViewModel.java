package usecase_adaptor.RecommendMovieWithoutFilter;

import entity.Movie;
import usecase_adaptor.MovieSearchByKeyword.SearchByNameState;
import usecase_adaptor.ViewModel;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

public class WithoutFilterViewModel extends ViewModel {

    public final String TITLE_LABEL = "Movie Recommendation View Without Filter";
    public final String USERNAME_LABEL = "Enter your username here";

    public static final String SEARCH_BUTTON_LABEL = "Search";
    public static final String MAIN_MENU_BUTTON_LABEL = "Back to Main Menu";

    private String usernameInput = "";
    private List<Movie> withoutFilterMovies;

    private WithoutFilterState withoutFilterState = new WithoutFilterState();


    private String error = null;

    public WithoutFilterViewModel() {
        super("movie_recommendation_filter");
    }


    public void setwatchlistidInput(String usernameInput) {
        this.usernameInput = usernameInput;
        firePropertyChanged();
    }

    public String getwatchlistidInput() {
        return usernameInput;
    }

    public String[] getRecommendedMovies() {
        List<String> movies = new ArrayList<>();
        if (this.withoutFilterMovies != null) {
            for (Movie movie : this.withoutFilterMovies) {
                movies.add(movie.getName());
            }
        }
        System.out.println("Called in result view model: " + movies);
        return movies.toArray(new String[0]);
    }

    public int getID(String movieTitle) {
        for (Movie movie : withoutFilterMovies) {
            if (movie.getName().equals(movieTitle)) {
                return movie.getID();
            }
        }
        return 0;
    }

    public void setWithoutFilterMovies(List<Movie> withoutFilterMovies) {
        this.withoutFilterMovies = withoutFilterMovies;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }

    public WithoutFilterState getState() {return this.withoutFilterState;}

    public void setState(WithoutFilterState withoutFilterState) {
        this.withoutFilterState = withoutFilterState;
    }



    private final PropertyChangeSupport support = new PropertyChangeSupport(this);


//    public void addErrorPropertyChangeListener(PropertyChangeListener listener) {
//        errorSupport.addPropertyChangeListener(listener);
//    }

    // This is what the MovieRecommendationPresenter will call to let the ViewModel know
    // to alert the View
    public void firePropertyChanged() {
        support.firePropertyChange("usernameInput", null, this.usernameInput);
    }

    public void fireRecommendedMoviesChanged() {
        support.firePropertyChange("withoutFilterMovies", null, usernameInput);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }
//    public void addErrorPropertyChangeListener(PropertyChangeListener listener) {
//        errorSupport.addPropertyChangeListener(listener);
//    }
//    private void fireErrorChanged() {
//        errorSupport.firePropertyChange("error", null, error);
//    }
}
