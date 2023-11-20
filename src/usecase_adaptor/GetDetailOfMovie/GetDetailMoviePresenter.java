package usecase_adaptor.GetDetailOfMovie;

import entity.Movie;
import use_case.GetDetailMovie.GetDetailMovieOutputBoundary;
import use_case.GetDetailMovie.GetDetailMovieOutputData;
import usecase_adaptor.ViewManagerModel;

import java.util.List;

public class GetDetailMoviePresenter implements GetDetailMovieOutputBoundary {
    private final GetDetailMovieViewModel getDetailMovieViewModel;

    private ViewManagerModel viewManagerModel;

    public GetDetailMoviePresenter(GetDetailMovieViewModel getDetailMovieViewModel,
                                   ViewManagerModel viewManagerModel) {
        this.getDetailMovieViewModel = getDetailMovieViewModel;
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void getDetailMovieSuccessView(GetDetailMovieOutputData response) {
        String title = response.getTitle();
        String overview = response.getOverview();
        List<String> genre = response.getGenre();
        List<String> actors = response.getActors();
        String poster_path = response.getPoster_path();
        GetDetailMovieState getDetailMovieState = getDetailMovieViewModel.getGetDetailMovieState();
        getDetailMovieState.setOverview(overview);
        getDetailMovieState.setActors(actors);
        getDetailMovieState.setGenre(genre);
        getDetailMovieState.setTitle(title);
        getDetailMovieState.setPoster_path(poster_path);
        getDetailMovieViewModel.setTitle(title);
        getDetailMovieViewModel.setOverview(overview);
        getDetailMovieViewModel.setActors(actors);
        getDetailMovieViewModel.setGenre(genre);
        getDetailMovieViewModel.setTitle(title);
        getDetailMovieViewModel.firePropertyChanged();

        viewManagerModel.setActiveView(getDetailMovieViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }

    @Override
    public void getDetailMovieFailView(String error) {
        GetDetailMovieState getDetailMovieState = getDetailMovieViewModel.getGetDetailMovieState();
        getDetailMovieState.setError(error);
        getDetailMovieViewModel.firePropertyChanged();
    }
}