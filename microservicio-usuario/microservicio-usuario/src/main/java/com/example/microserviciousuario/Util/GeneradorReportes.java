package com.example.microserviciousuario.Util;

import com.example.microserviciousuario.model.Evento;
import com.example.microserviciousuario.servicios.UserServices;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.util.*;

@Service
public class GeneradorReportes {

    public byte[] generarReportePDF(Evento evento, String nombreCliente) throws JRException, IOException {
        JasperReport report = JasperCompileManager.compileReport(new ClassPathResource("resumenCompra.jrxml").getInputStream());

        InputStream logoStream = new ClassPathResource("static/img/logo syh.png").getInputStream();

        Map<String, Object> parametros = new HashMap<>();
        parametros.put("nombreCliente", nombreCliente);
        parametros.put("total", evento.getTotal());
        parametros.put("imgLogo", logoStream);


        //JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(Collections.singletonList(evento));

        List<Evento> eventos = new ArrayList<>();
        eventos.add(evento);
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(eventos);

        parametros.put("dsInvoice", dataSource);


        JasperPrint jasperPrint = JasperFillManager.fillReport(report, parametros, dataSource);

        return JasperExportManager.exportReportToPdf(jasperPrint);
    }

    public byte[] exportToPdf(Evento evento, String nombreCliente) throws JRException, IOException {
        List<Evento> list = new ArrayList<>();
        list.add(evento);
        return JasperExportManager.exportReportToPdf(getReport(list, evento, nombreCliente));
    }


    private JasperPrint getReport(List<Evento> list, Evento evento, String nombreCliente) throws IOException, JRException {
        Map<String, Object> params = new HashMap<String, Object>();
        InputStream logoStream = new ClassPathResource("static/img/logo syh.png").getInputStream();

        params.put("dsInvoice", new JRBeanCollectionDataSource(list));
        params.put("nombreCliente", nombreCliente);
        params.put("total", evento.getTotal());
        params.put("imgLogo", logoStream);
        JasperPrint report = JasperFillManager.fillReport(JasperCompileManager.compileReport(
                ResourceUtils.getFile("classpath:resumenCompra.jrxml")
                        .getAbsolutePath()), params, new JREmptyDataSource());

        return report;
    }

}
