package com.sysware.elasticsearch.controller;

import com.sysware.elasticsearch.domain.NbaPlayer;
import com.sysware.elasticsearch.service.NbaPlayerService;
import com.sysware.mybatis.domain.JsonData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 描述：用ES模拟NBA球员关系查询
 *
 * @Author: zwx
 * @Company 云迹科技
 * @Date: 2019/10/15 11:32
 */
@RestController
@RequestMapping("api/v1/es")
@Api(value = "elasticsearch相关API", tags = {"elasticsearch操作接口"})
public class NbaPlayerController {

    @Autowired
    private NbaPlayerService nbaPlayerService;

    @ApiOperation(value = "新增球员", notes = "")
    @PostMapping("add")
    public Object addNbaPlayer(@RequestBody NbaPlayer nbaPlayer) throws IOException {
        String msg = nbaPlayerService.addNbaPlayer(nbaPlayer);
        return JsonData.buildSuccess(msg);
    }

    @ApiOperation(value = "删除球员", notes = "")
    @DeleteMapping("remove")
    public Object removeNbaPlayer(@RequestParam("id") String id) throws IOException {
        nbaPlayerService.removeNbaPlayer(id);
        return JsonData.buildSuccess();
    }

    @ApiOperation(value = "删除全部球员", notes = "")
    @DeleteMapping("remove/all")
    public Object removeAllNbaPlayer() throws IOException {
        nbaPlayerService.removeAll();
        return JsonData.buildSuccess();
    }

    @ApiOperation(value = "更新球员", notes = "")
    @PutMapping("update")
    public Object updateNbaPlayer(@RequestBody NbaPlayer nbaPlayer) throws IOException {
        nbaPlayerService.updateNbaPlayer(nbaPlayer);
        return JsonData.buildSuccess();
    }

    @ApiOperation(value = "获取球员", notes = "")
    @GetMapping("get")
    public Object getNbaPlayer(@RequestParam("id") String id) throws IOException {
        Map<String, Object> msg = nbaPlayerService.getNbaPlayer(id);
        return JsonData.buildSuccess(msg);
    }

    @ApiOperation(value = "从数据库导入到ES", notes = "")
    @PostMapping("import")
    public Object importAll() throws IOException {
        nbaPlayerService.importAll();
        return JsonData.buildSuccess();
    }

    @ApiOperation(value = "根据球员名称模糊查询", notes = "match查询（text类型），其他text类型字段也通用，输入正确的字段名即可")
    @PostMapping("search/match")
    public Object searchMatch(@RequestParam("key") String key, @RequestParam("value") String value,
                              @RequestParam("page") int page, @RequestParam("size") int size) throws IOException {
        List<NbaPlayer> msg = nbaPlayerService.searchMatch(key, value, page, size);
        return JsonData.buildSuccess(msg);
    }

    @ApiOperation(value = "根据球队/国家查询球员",
            notes = "term查询（keyword类型），其他keyword类型字段也通用，输入正确的字段名即可，" +
            "注意：字段要填写teamNameEn.keyword，如果只填teamNameEn，是一个text类型，无法查到数据")
    @PostMapping("search/term")
    public Object searchTerm(@RequestParam("key") String key, @RequestParam("value") String value,
                              @RequestParam("page") int page, @RequestParam("size") int size) throws IOException {
        List<NbaPlayer> msg = nbaPlayerService.searchTerm(key, value, page, size);
        return JsonData.buildSuccess(msg);
    }

    @ApiOperation(value = "根据球员名称首字母模糊查询", notes = "match查询--Prefix前缀查询，其他text类型字段也通用，输入正确的字段名即可")
    @PostMapping("search/match/prefix")
    public Object searchMatchPrefix(@RequestParam("key") String key, @RequestParam("value") String value,
                              @RequestParam("page") int page, @RequestParam("size") int size) throws IOException {
        List<NbaPlayer> msg = nbaPlayerService.searchMatchPrefix(key, value, page, size);
        return JsonData.buildSuccess(msg);
    }
}
