package com.justforfun.app.api.users.data.user;

import com.justforfun.app.api.users.ui.model.AlbumResponseModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name="jff-albums")
public interface AlbumServiceClient {

    @GetMapping("/users/{id}/albums") //get the path from the GetMapping in AlbumController
    //public void getAlbums(@PathVariable String id);
    public List<AlbumResponseModel> getAlbums(@PathVariable String id);

}
