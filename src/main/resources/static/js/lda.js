d3.selectAll(".expandVolumeTitle")
    .on("click", function() {
        var volume = this.id.split("_")[1];
        d3.select("#volume_" + volume)
            .style("display", "block");
        d3.select("#collapseVolumeTitle_" + volume)
            .style("display", "block");
        d3.select(this)
            .style("display", "none");
    });
d3.selectAll(".collapseVolumeTitle")
    .on("click", function() {
        var volume = this.id.split("_")[1];
        d3.select("#volume_" + volume)
            .style("display", "none");
        d3.select("#expandVolumeTitle_" + volume)
            .style("display", "block");
        d3.select(this)
            .style("display", "none");
    });