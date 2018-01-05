var corpusName = d3.select("#corpusName").text();
var numberOfTopics = d3.select("#numberOfTopics").text();
var timestamp = d3.select("#timestamp").text();
var ldaUrl = "http://localhost:8080/corpus/" + corpusName + "/numberOfTopics/" + numberOfTopics + "/timestamp/" + timestamp;

var util = {
    buildTable: function (data, tableName, key, value, noLinks) {
        var table = d3.select("#" + tableName + "Table");
        var tbody = table.append("tbody");
        var max = data[0]["probability"];
        var filteredData = data.filter(function(row) { return row["probability"] >= 0.01; });
        showMore(tbody, filteredData, max, 10, key, value, noLinks);
        appendButtons(table, tableName, tbody, filteredData, max, key, value, noLinks);

        function showMore(tbody, data, max, n, linkKey, linkValue, plaintext) {
            var currentLength = tbody.selectAll("tr").size();
            var rows = tbody.selectAll("tr")
                .data(data.slice(0, currentLength + n))
                .enter()
                .append("tr");
            if (linkKey)
                appendLinks(rows, linkKey, linkValue);
            if (plaintext)
                appendText(rows, plaintext);
            appendProbabilities(rows, max);
        }

        function showLess(tbody, data, n) {
            var currentLength = tbody.selectAll("tr").size();
            var rows = tbody.selectAll("tr")
                .data(data.slice(0, currentLength - n))
                .exit()
                .remove();
        }

        function appendButtons(table, tableName, tbody, data, max, linkKey, linkValue, plaintext) {
            var tableParent = d3.select(table.node().parentNode);
            tableParent.append("button")
                .attr("class", "more")
                .attr("id", tableName + "More")
                .text("+")
                .on("click", function () {
                    showMore(tbody, data, max, 5, linkKey, linkValue, plaintext);
                });
            tableParent.append("button")
                .attr("class", "less")
                .attr("id", tableName + "Less")
                .text("-")
                .on("click", function () {
                    showLess(tbody, data, 5);
                });
        }

        function appendProbabilities(rows, max) {
            rows.append("td").classed("weight", true)
                .append("div")
                .classed("proportion", true)
                .style("margin-left", function (row) {
                    return d3.format(".1%")(1 - row['probability'] / max);
                })
                .append("span")
                .classed("proportion", true)
                .text(function (row) {
                    return row['probability'];
                });

            rows.append("td")
                .text(function (row) {
                    return d3.format(".2%")(row['probability']);
                });
        }

        function appendLinks(rows, key, value) {
            rows.append("td")
                .append("a")
                .attr("href", function (row) {
                    return ldaUrl + "/" + key + "/" + row[value];
                })
                .attr("class", value)
                .text(function (row) {
                    return row[value];
                });
        }

        function appendText(rows, value) {
            rows.append("td")
                .attr("class", value)
                .text(function (row) {
                    return row[value];
                });
        }
    }
}