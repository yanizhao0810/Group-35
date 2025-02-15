package view;

import usecase_adaptor.GetDetailOfMovie.GetDetailMovieController;
import usecase_adaptor.MovieSearchByKeyword.MovieResultViewModel;
import usecase_adaptor.RecommendMovieWithoutFilter.WithoutFilterResultState;
import usecase_adaptor.RecommendMovieWithoutFilter.WithoutFilterResultViewModel;
import usecase_adaptor.RecommendMovieWithoutFilter.WithoutFilterViewModel;
import usecase_adaptor.ViewManagerModel;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Arrays;

public class WithoutFilterResultView extends JPanel implements PropertyChangeListener {
    public final String viewName = "without_result";
    private final WithoutFilterResultViewModel viewModel;
    private final WithoutFilterViewModel withoutFilterViewModel;

    private final ViewManagerModel viewManagerModel;

    final JButton mainMenuBtn;
    private final GetDetailMovieController controller;

    public DefaultListModel<String> listModel;
    public JList<String> movieList;

    //    private JTextArea movieTextArea = new JTextArea();
    JLabel errorLabel;

    public WithoutFilterResultView(WithoutFilterResultViewModel viewModel, WithoutFilterViewModel withoutFilterViewModel, ViewManagerModel viewManagerModel, GetDetailMovieController getDetailMovieController) {
        this.controller = getDetailMovieController;
        this.withoutFilterViewModel = withoutFilterViewModel;
        setVisible(false);
        this.viewManagerModel = viewManagerModel;
        this.viewModel = viewModel;
        this.viewModel.addPropertyChangeListener(this);

        setLayout(new BorderLayout());
        JPanel buttons = new JPanel(new FlowLayout());
        mainMenuBtn = new JButton(MovieResultViewModel.MAIN_MENU_LABEL);
        buttons.add(mainMenuBtn);

        mainMenuBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if (evt.getSource().equals(mainMenuBtn)) {
                    viewManagerModel.setActiveView("movie_recommendation_filter");
                    viewManagerModel.firePropertyChanged();
                }
            }
        });

//        setLayout(new BorderLayout());

        JPanel titlePanel = new JPanel();
        JLabel title = new JLabel("Your Movie Recommendations");
        titlePanel.add(title);
//        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel movieListPanel = new JPanel(new BorderLayout());
        listModel = new DefaultListModel<>();
        movieList = new JList<>(listModel);
        listModel.clear();
        String[] withoutFilterMovies = withoutFilterViewModel.getRecommendedMovies();


        // Update movie list
        if (withoutFilterMovies != null) {
            listModel.addAll(Arrays.asList(withoutFilterMovies));
        }

        // Update error label
        movieList.setModel(listModel);

        movieList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int selectedIndex = movieList.getSelectedIndex();
                    if (selectedIndex != -1) {
                        // Get the selected movie name
                        String selectedMovie = listModel.getElementAt(selectedIndex);

                        // Handle the click event, e.g., switch to another view
                        handleMovieClick(selectedMovie);
                    }
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(movieList);
        movieListPanel.add(scrollPane, BorderLayout.CENTER);
        this.add(titlePanel, BorderLayout.NORTH);
        this.add(movieListPanel, BorderLayout.CENTER);
        this.add(buttons, BorderLayout.SOUTH);
        String error = viewModel.getError();

        // Error label
        errorLabel = new JLabel(error);
        // this.add(errorLabel, BorderLayout.SOUTH);

        setVisible(true);
    }


    @Override
    public void propertyChange(PropertyChangeEvent evt) {
//        List<String> recommendedMovies = viewModel.getRecommendedMovies();
//        String error = viewModel.getError();
//        StringBuilder movieText = new StringBuilder();
//
//        // Update movie text area
//        if (recommendedMovies != null) {
//            for (String name : recommendedMovies) {
//                movieText.append(name).append("\n");
//                System.out.println(name);
//            }
//            movieTextArea.setText(movieText.toString());
//        }
//
//        // Update error label
//        errorLabel.setText(error);
//
//        // Repaint the UI
//        revalidate();
//        repaint();
//        setVisible(true);
//
//        updateView();
//        if ("recommendedMovies".equals(evt.getPropertyName())) {
//            updateView();
//        }

        switch (evt.getPropertyName()) {
            case "withoutFilterMovies":
                WithoutFilterResultState state = viewModel.getState();
                if (state.getError() != null) {
                    JOptionPane.showMessageDialog(this, state.getError());
                } else {
                    updateView();
                }
            case "error":
                // Handle error property change if needed
                break;
            // Add more cases if there are other properties to handle
        }
    }

    public void updateView() {
        SwingUtilities.invokeLater(() -> {
            System.out.println("Updating view...");

            // Print some information for debugging
            System.out.println("WithoutFilterViewModel: " + withoutFilterViewModel);
            System.out.println("viewModel: " + viewModel);

            String[] withoutFilterMovies = withoutFilterViewModel.getRecommendedMovies();
            System.out.println("Recommended movies: " + Arrays.toString(withoutFilterMovies));

            if (withoutFilterMovies != null) {
                listModel.clear();
                listModel.addAll(Arrays.asList(withoutFilterMovies));
                movieList.setModel(listModel);

                // Print the contents of listModel for debugging
                System.out.println("Contents of listModel:");
                for (int i = 0; i < listModel.size(); i++) {
                    System.out.println(listModel.getElementAt(i));
                }

                // Update error label
                errorLabel.setText(viewModel.getError());

                // Repaint the UI
                revalidate();
                repaint();
            } else {
                System.out.println("Recommended movies is null.");
            }

            System.out.println("Update complete.");
        });
    }

    private void handleMovieClick(String selectedMovie) {
        String username = withoutFilterViewModel.getState().getUsername();
        int movie_id = withoutFilterViewModel.getID(selectedMovie);
        controller.execute(movie_id, username);
        viewManagerModel.setActiveView("detail_view");
        viewManagerModel.firePropertyChanged();
        System.out.println("Movie clicked");
    }
}












