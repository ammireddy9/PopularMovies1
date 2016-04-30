package com.example.laxmi.popularmovies1;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    MovieObjAdapter movieObjAdapter;
    GridView gridView;
    View view;
    MovieObj[] movieArray = null;

    public MainActivityFragment() {}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.latest) {
            getPopularMovies();
            return true;
        }
        else if(id==R.id.top_rated)
        {
            getHighRatedMovies();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main, container, false);
        gridView = (GridView) view.findViewById(R.id.flavors_grid);
        gridView.setAdapter(movieObjAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getActivity(),Main2Activity.class);
                intent.putExtra("object",movieArray[position]);
                startActivity(intent);
            }
        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        getPopularMovies();
    }

    public void getPopularMovies() {
        String add="http://api.themoviedb.org/3/movie/popular?api_key=";
        FetchPopularMovie fetchPopularMovie = new FetchPopularMovie();
        fetchPopularMovie.execute(add);
    }

    public void getHighRatedMovies() {
        String rated="http://api.themoviedb.org/3/movie/top_rated?api_key=";
        FetchPopularMovie fetchPopularMovie = new FetchPopularMovie();
        fetchPopularMovie.execute(rated);
    }

    public class FetchPopularMovie extends AsyncTask<String, Void, String> {

        protected String doInBackground(String...address) {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            String movieJsonStr = null;
            try {
                //String apiKey = BuildConfig.API_KEY;
                String apiKey="42485f4880f7eccf3e03c7cc3347ea00";
                URL url = new URL(address[0] + apiKey);

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                if (urlConnection.getResponseCode() != 200) {
                    Log.d("Response : ", "" + urlConnection.getResponseCode());
                    return null;
                }
                InputStream inputStream = urlConnection.getInputStream();

                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    movieJsonStr = null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }
                movieJsonStr = buffer.toString();
            } catch (IOException e) {
                e.printStackTrace();

            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e("Fetch", "Error closing stream", e);
                    }
                }
            }

            return movieJsonStr;

        }

        @Override
        protected void onPostExecute(String popularMovieArrayList) {
            movieArray = getPopularMoviesJson(popularMovieArrayList);
            movieObjAdapter = new MovieObjAdapter(getActivity(), movieArray);
            gridView.setAdapter(movieObjAdapter);
        }

        private MovieObj[] getPopularMoviesJson(String movieJsonStr) {
            String poster_path = "backdrop_path";
            String movie_id = "id";
            String movie_title = "original_title";
            String posterBasePath = "http://image.tmdb.org/t/p/w185";
            String movieOverview = "overview";
            String releaseDate = "release_date";
            String userRating = "vote_average";

            try {
                JSONObject movieJsonObject = new JSONObject(movieJsonStr);
                JSONArray movieJsonObjectJSONArray = movieJsonObject.getJSONArray("results");
                movieArray = new MovieObj[movieJsonObjectJSONArray.length()];

                for (int i = 0; i < movieArray.length; i++) {
                    // For now, using the format "Day, description, hi/low"
                    JSONObject movieObject = movieJsonObjectJSONArray.getJSONObject(i);
                    MovieObj popularMovie = new MovieObj();

                    popularMovie.image = posterBasePath + movieObject.getString(poster_path);
                    popularMovie.id = movieObject.getString(movie_id);
                    popularMovie.release_date=movieObject.getString(releaseDate);
                    popularMovie.title=movieObject.getString(movie_title);
                    popularMovie.synopsis=movieObject.getString(movieOverview);
                    popularMovie.user_rating=movieObject.getString(userRating);
                    movieArray[i] = popularMovie;

                    // Get the JSON object representing the day
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.v("movie array list size", "::::" + movieArray.length);
            return movieArray;

        }


    }


}
