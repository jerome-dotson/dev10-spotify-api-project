import { useEffect, useState } from 'react';
import {getCurrentUserProfile} from '../SpotifyManager';

function Test(){

    const [profile, setProfile] = useState(null);

    const fetchData = async () =>{
        try{
            const {data} = await getCurrentUserProfile();
            setProfile(data);

            console.log(data);
        }catch(e){
            console.error(e);
        }

    }

    fetchData();
}

export default Test;