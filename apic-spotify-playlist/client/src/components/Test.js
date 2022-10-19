import { useEffect, useState } from 'react';
import {createPlaylist, getCurrentUserProfile} from '../SpotifyManager';

function Test(){

    let data = getCurrentUserProfile();
    
    (console.log(data));
    console.log("Hello");

    
}

export default Test;