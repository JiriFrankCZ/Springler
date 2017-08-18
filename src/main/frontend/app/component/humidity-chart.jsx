import React from 'react';
import {Line} from 'react-chartjs-2';
import moment from "moment";

export class HumidityChart extends React.Component{
	constructor(props) {
		super(props);
		this.state = {values: [], labels: []};
	}

	componentWillUnmount() {
		clearInterval(this.interval);
	}

	componentDidMount() {
		this.interval = setInterval(this.HumidityChart, 15000);
		this.HumidityChart();
	}

	HumidityChart() {
		return $.getJSON('https://springler.herokuapp.com/humidity/latest')
			.then((data) => {
				let values = [];
				let labels = [];

				{data.map((humidity) => {
						values.unshift(100 - (humidity.value / 10));
						labels.unshift(moment(humidity.dateTime).format('HH:mm:ss'));
					}
				)}

				this.setState({
					values: values,
					labels: labels
				});
			});
	}

	render() {
		let data = {
			labels: this.state.labels,
			datasets: [{
				label: '',
				data: this.state.values,
				lineTension: 0,
				borderColor: '#3BAFDA'
			}]
		};

		let styles = {
			padding: '15px'
		}

		return <div className="panel panel-default">
			<div className="panel-heading">Humidity</div>
			<div style={styles}>
				<Line ref='chart' data={data} height={100}/>
			</div>
		</div>;
	};
}