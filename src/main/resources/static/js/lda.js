d3.selectAll(".expandButton")
    .on("click", function() {
        var volume = this.id.split("_")[1];
        d3.select("#volume_" + volume)
            .style("display", "block");
        d3.select("#collapseButton_" + volume)
            .style("display", "block");
        d3.select(this)
            .style("display", "none");
    });
d3.selectAll(".collapseButton")
    .on("click", function() {
        var volume = this.id.split("_")[1];
        d3.select("#volume_" + volume)
            .style("display", "none");
        d3.select("#expandButton_" + volume)
            .style("display", "block");
        d3.select(this)
            .style("display", "none");
    });