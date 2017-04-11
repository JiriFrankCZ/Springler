package cz.jirifrank.app.springler.config;

import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.node.NodeClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.node.NodeBuilder;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;

@Configuration
@EnableConfigurationProperties(ElasticsearchProperties.class)
@Slf4j
public class ElasticSearchConfiguration implements DisposableBean {


	@Autowired
	private ElasticsearchProperties properties;

	private NodeClient client;

	@Bean
	public ElasticsearchTemplate elasticsearchTemplate(Client client) {
		return new ElasticsearchTemplate(client);
	}

	@Bean
	public Settings elasticsearchSettings() {

		Settings.Builder elasticsearchSettings = Settings.settingsBuilder()
				.put("path.home", "/elastic");

		return elasticsearchSettings.build();
	}

	@Bean
	public Client esClient(Settings elasticsearchSettings) {
		try {
			if (log.isInfoEnabled()) {
				log.info("Starting Elasticsearch client");
			}
			NodeBuilder nodeBuilder = new NodeBuilder();
			nodeBuilder
					.settings(elasticsearchSettings)
					.clusterName(this.properties.getClusterName())
					.local(false)
			;
			nodeBuilder.settings()
					.put("http.enabled", true)
			;
			this.client = (NodeClient) nodeBuilder.node().client();
			return this.client;
		} catch (Exception ex) {
			throw new IllegalStateException(ex);
		}
	}

	@Override
	public void destroy() throws Exception {
		if (this.client != null) {
			try {
				if (log.isInfoEnabled()) {
					log.info("Closing Elasticsearch client");
				}
				if (this.client != null) {
					this.client.close();
				}
			} catch (final Exception ex) {
				if (log.isErrorEnabled()) {
					log.error("Error closing Elasticsearch client: ", ex);
				}
			}
		}
	}
}