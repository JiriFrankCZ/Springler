import React from 'react';
import {Activity} from "./activity";

export class Activities extends React.Component {

	constructor(props) {
		super(props);
		this.state = {activities: []};
		this.Activities = this.Activities.bind(this);
	}

	componentDidMount() {
		this.interval = setInterval(this.Activities, 15000);
		this.Activities();
	}

	componentWillUnmount() {
		clearInterval(this.interval);
	}

	Activities() {
		return $.getJSON('https://springler.herokuapp.com/logging/' + this.props.size)
			.then((data) => {
				this.setState({activities: data});
			});
	}

	render() {
		return <div className="panel panel-default">
			<div className="panel-heading">Activities</div>
			<table className="table">
				<thead>
				<tr>
					<th>Date</th>
					<th>Action</th>
					<th>Option</th>
				</tr>
				</thead>
				<tbody>
				{this.state.activities.map((activity, i) => {
						return <Activity data={activity} key={i}/>
					}
				)}
				</tbody>
			</table>
		</div>;
	};
}