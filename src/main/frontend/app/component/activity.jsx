import React from 'react';
import moment from 'moment';

export class Activity extends React.Component {

	render() {
		let action;
		let color;

		switch(this.props.data.action){
			case 'STANDARD_WATERING':
				action = 'Standard watering';
				color = 'success';
				break;
			case 'MANUAL_WATERING':
				action = 'Manual watering triggered';
				color = 'info';
				break;
			case 'EMERGENCY_WATERING':
				action = 'Emergency watering triggered';
				color = 'warning';
				break;
			case 'WATERING_BYPASSED_BY_WEATHER':
				action = 'Watering needed but forecast predicts rain';
				 color = 'warning';
				break;
			case 'WATERING_NOT_NEEDED':
				action = 'Watering is not needed';
				color = 'success';
				break;
			case 'ALERT':
				action = 'Technical alert';
				color = 'danger';
				break;
			case 'REPORTING':
				action = 'Reporting performed';
				color = 'info';
				break;
		}

		return (
		<tr key={this.props.data.key} className={color}>
			<td>{moment(this.props.data.dateTime).format('DD.MM.YYYY hh:mm:ss')}</td>
			<td>{action}</td>
			<td>{this.props.data.value}</td>
		</tr>
		);
	};
}