var colorRamp = d3.scaleLinear()
    .domain([0, numberOfTopics * .25, numberOfTopics * .5, numberOfTopics * .75, numberOfTopics])
    .range(["red", "blue", "green", "cyan", "magenta"]);

d3.selectAll(".expandVolumeTitle")
    .on("click", function () {
        var volume = this.id.split("_")[1];
        d3.select("#volume_" + volume)
            .style("display", "block");
        d3.select("#collapseVolumeTitle_" + volume)
            .style("display", "block");
        d3.select(this)
            .style("display", "none");
    });
d3.selectAll(".collapseVolumeTitle")
    .on("click", function () {
        var volume = this.id.split("_")[1];
        d3.select("#volume_" + volume)
            .style("display", "none");
        d3.select("#expandVolumeTitle_" + volume)
            .style("display", "block");
        d3.select(this)
            .style("display", "none");
    });

d3.selectAll(".topicsButton")
    .on("click", function () {
        var documentTitle = this.id.split("_")[1];
        var documentUrl = "http://localhost:8080/api/corpus/" + corpusName + "/numberOfTopics/" + numberOfTopics + "/timestamp/" + timestamp + "/document/" + documentTitle;
        d3.request(decodeURI(documentUrl))
            .mimeType("application/json")
            .response(function (xhr) {
                return JSON.parse(xhr.responseText);
            })
            .get(function (data) {
                var topicProbabilities = data.topicProbabilities.map(
                    function (row) {
                        var topic = row.topic;
                        var topicProbability = {};
                        topicProbability["topicId"] = topic.topicId;
                        topicProbability["topWords"] = topic.topWords;
                        topicProbability["probability"] = row.probability;
                        return topicProbability
                    }
                )
                util.buildTable(topicProbabilities, documentTitle, "topic", "topicId", "topWords");
                d3.selectAll(".topWords")
                    .style("color", function () {
                        var parentNode = d3.select(this).node().parentNode;
                        var topicId = d3.select(parentNode).select("a").text();
                        d3.select(this)
                            .attr("id", topicId)
                            .on("click", function () {
                                markTopic(".topWords", topicId);
                                markTopic(".contentToken", topicId);
                            });
                        return colorRamp(topicId);
                    });
            });

    });