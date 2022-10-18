import axios from "axios";


axios.defaults.baseURL = 'https://api.spotify.com/v1';
axios.defaults.headers['Authorization'] = `Bearer ${window.localStorage.getItem("current_spotify_access_token")}`; //Will need to add a way for collaborators to use this
axios.defaults.headers['Content-Type'] = 'application/json';

/**
 * Get Current User's Profile
 * https://developer.spotify.com/documentation/web-api/reference/#endpoint-get-current-users-profile
 * @returns {Promise}
 */


//For functions that return below, through Axios, they can be destructured with { data } to access the JSON data returned
//or (*function_call_or_function_call_holder_variable*).data

 export const getCurrentUserProfile = () => axios.get('/me'); //returns response that can be destructured into object. Use
 //example syntax is const { data } = getCurrentUserProfile(); Should be called asynchronously with 
 //Reference URL for what method returns: https://developer.spotify.com/documentation/web-api/reference/#/operations/get-current-users-profile


export const getCurrentUserPlaylists = (limit = 20) => {
    return axios.get(`/me/playlists?limit=${limit}`); //Gets a list of the current user's playlists. Returns a JSON with an array of playlist objects
    //Default limit is the maximum number of playlists returned. 
    //Reference URL for what method returns: https://developer.spotify.com/documentation/web-api/reference/#/operations/get-a-list-of-current-users-playlists

}