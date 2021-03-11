package com.iexec.common.docker;

import com.github.dockerjava.api.model.Device;
import com.iexec.common.utils.SgxUtils;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class DockerRunRequestTests {

    @Test
    public void shouldAddDevices() {
        Device device1 = new Device("", "/dev/path1", "/dev/path1");
        Device device2 = new Device("", "/dev/path2", "/dev/path2");
        List<Device> devices = new ArrayList<>();
        devices.add(device1);
        devices.add(device2);
        DockerRunRequest request = DockerRunRequest.builder()
                .containerName("containerName")
                .devices(devices)
                .build();
        System.out.println(request);
        assertThat(request.getDevices().get(0)).isEqualTo(device1);
        assertThat(request.getDevices().get(1)).isEqualTo(device2);
    }

    @Test
    public void shouldAddSgxDevice() {
        DockerRunRequest request = DockerRunRequest.builder()
                .containerName("containerName")
                .isSgx(true)
                .build();
        System.out.println(request);
        assertThat(request.getDevices().get(0).getcGroupPermissions())
                .isEqualTo(SgxUtils.SGX_CGROUP_PERMISSIONS);        
        assertThat(request.getDevices().get(0).getPathInContainer())
                .isEqualTo(SgxUtils.SGX_DEVICE_PATH);
        assertThat(request.getDevices().get(0).getPathOnHost())
                .isEqualTo(SgxUtils.SGX_DEVICE_PATH);
    }

    @Test
    public void shouldAddSgxDeviceThenAddDevices() {
        Device device1 = new Device("", "/dev/path1", "/dev/path1");
        Device device2 = new Device("", "/dev/path2", "/dev/path2");
        List<Device> devices = new ArrayList<>();
        devices.add(device1);
        devices.add(device2);
        DockerRunRequest request = DockerRunRequest.builder()
                .containerName("containerName")
                .isSgx(true) // notice order here
                .devices(devices)
                .build();
        System.out.println(request);
        assertThat(request.getDevices().get(0).getcGroupPermissions())
                .isEqualTo(SgxUtils.SGX_CGROUP_PERMISSIONS);        
        assertThat(request.getDevices().get(0).getPathInContainer())
                .isEqualTo(SgxUtils.SGX_DEVICE_PATH);
        assertThat(request.getDevices().get(0).getPathOnHost())
                .isEqualTo(SgxUtils.SGX_DEVICE_PATH);
        assertThat(request.getDevices().get(1)).isEqualTo(device1);
        assertThat(request.getDevices().get(2)).isEqualTo(device2);
    }

    @Test
    public void shouldAddDeviceThenAddSgxDevice() {
        Device device1 = new Device("", "/dev/path1", "/dev/path1");
        Device device2 = new Device("", "/dev/path2", "/dev/path2");
        List<Device> devices = new ArrayList<>();
        devices.add(device1);
        devices.add(device2);
        DockerRunRequest request = DockerRunRequest.builder()
                .containerName("containerName")
                .devices(devices) // changed order here
                .isSgx(true)
                .build();
        System.out.println(request);
        assertThat(request.getDevices().get(0)).isEqualTo(device1);
        assertThat(request.getDevices().get(1)).isEqualTo(device2);
        assertThat(request.getDevices().get(2).getcGroupPermissions())
                .isEqualTo(SgxUtils.SGX_CGROUP_PERMISSIONS);        
        assertThat(request.getDevices().get(2).getPathInContainer())
                .isEqualTo(SgxUtils.SGX_DEVICE_PATH);
        assertThat(request.getDevices().get(2).getPathOnHost())
                .isEqualTo(SgxUtils.SGX_DEVICE_PATH);
    }
}
