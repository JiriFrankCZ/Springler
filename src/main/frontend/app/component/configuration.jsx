import React from 'react';

export class Configuration extends React.Component {

	constructor(props) {
		super(props);
		this.state = {configuration: []};
		this.handleSubmit = this.handleSubmit.bind(this);
	}

	componentDidMount() {
		this.Configuration();
	}

	Configuration() {
		return $.getJSON('https://springler.herokuapp.com/configuration/')
			.then((data) => {
				this.setState({configuration: data});
			});
	}

	handleSubmit(event) {
		$.ajax({
			url: 'https://springler.herokuapp.com/configuration/threshold/standard/' + this.state.configuration.wateringThresholdStandard,
			type: 'PUT'
		});

		$.ajax({
			url: 'https://springler.herokuapp.com/configuration/threshold/emergency/' + this.state.configuration.wateringThresholdEmergency,
			type: 'PUT'
		});

		event.preventDefault();
	}

	render() {
		return <div className="panel panel-default">
			<div className="panel-heading">Configuration</div>
			<div className="row">
				<div className="col-md-12">
					<h2>Watering</h2>
					<form onSubmit={this.handleSubmit}>
						<input type="text" value={this.state.configuration.wateringThresholdStandard}
						       className="form-control" placeholder="Standard treshold"/>
						<input type="text" value={this.state.configuration.wateringThresholdEmergency}
						       className="form-control" placeholder="Emergency threshold"/>
						<input type="submit" value="Submit"/>
					</form>
				</div>
			</div>
		</div>;
	};
}