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
            <div className="padding-15">
                <form onSubmit={this.handleSubmit}>
                    <div class="form-group">
                        <label for="wateringThresholdStandard">Watering threshold standard</label>

                        <input type="text" value={this.state.configuration.wateringThresholdStandard}
                               className="form-control" id="wateringThresholdStandard"/>
                    </div>
                    <div class="form-group">
                        <label for="wateringThresholdStandard">Watering threshold standard</label>

                        <input type="text" value={this.state.configuration.wateringThresholdEmergency}
                               className="form-control" id="wateringThresholdEmergency"/>
                    </div>
                    <div class="form-group">
                        <button type="submit" className="btn btn-default">Submit</button>
                    </div>
                </form>
            </div>
        </div>;
    };
}